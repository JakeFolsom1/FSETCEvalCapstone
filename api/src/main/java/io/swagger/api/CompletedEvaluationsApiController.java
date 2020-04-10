package io.swagger.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiParam;
import io.swagger.model.*;
import io.swagger.repository.AssignmentRepository;
import io.swagger.repository.EvaluationsReleasedRepository;
import io.swagger.repository.QuestionRepository;
import io.swagger.repository.ResponseRepository;
import io.swagger.util.TmsApiHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-11-07T08:20:05.576Z")

@Controller
public class CompletedEvaluationsApiController implements CompletedEvaluationsApi {

    private static final Logger log = LoggerFactory.getLogger(CompletedEvaluationsApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private ResponseRepository responseRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private EvaluationsReleasedRepository evaluationsReleasedRepository;

    @Autowired
    private TmsApiHelper tmsApiHelper;

    @org.springframework.beans.factory.annotation.Autowired
    public CompletedEvaluationsApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<List<CompletedEvaluation>> getAllCompletedEvaluations() {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            List<Assignment> completedAssignments = assignmentRepository.findAllByIsComplete(true);
            List<CompletedEvaluation> completedEvaluationList = new ArrayList<CompletedEvaluation>();
            for (Assignment assignment: completedAssignments) {
                CompletedEvaluation completedEvaluation = new CompletedEvaluation();
                completedEvaluation.setEvalType(assignment.getEvalType().name());
                // Need to get the staff from the TMS API from the term it was completed
                Staff evaluator = tmsApiHelper.getStaffByAsuriteAndSemester(assignment.getAsurite(), assignment.getSemesterName());
                Staff evaluatee = tmsApiHelper.getStaffByAsuriteAndSemester(assignment.getAssignedAsurite(), assignment.getSemesterName());
                completedEvaluation.setEvaluator(evaluator.getFname() + " " + evaluator.getLname());
                completedEvaluation.setEvaluatee(evaluatee.getFname() + " " + evaluatee.getLname());
                completedEvaluation.setSemester(assignment.getSemesterName());
                List<Response> responses = responseRepository.findAllByAssignmentIdOrderByQuestionIdAsc(assignment.getAssignmentId());
                if (responses.isEmpty()) {
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
                completedEvaluation.setIsShared(responses.get(0).isIsShared());
                List<QuestionAndResponse> questionsAndResponses = new ArrayList<QuestionAndResponse>();
                for (Response response: responses) {
                    QuestionAndResponse questionAndResponse = new QuestionAndResponse();
                    QuestionDetails questionDetails = new QuestionDetails();
                    Question question = questionRepository.findOne(response.getQuestionId());
                    questionDetails.setQuestionNumber(question.getQuestionNumber());
                    questionDetails.setQuestionPrompt(question.getQuestionPrompt());
                    questionDetails.setQuestionType(question.getQuestionType().name());
                    questionAndResponse.setQuestion(questionDetails);
                    questionAndResponse.setResponse(response.getResponse());
                    questionsAndResponses.add(questionAndResponse);
                }
                completedEvaluation.setQuestionsAndResponses(questionsAndResponses);
                completedEvaluationList.add(completedEvaluation);
            }
            return new ResponseEntity<List<CompletedEvaluation>>(completedEvaluationList, HttpStatus.OK);
        }

        return new ResponseEntity<List<CompletedEvaluation>>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<CompletedEvaluation>> getSharedUserEvaluations(@ApiParam(value = "",required=true) @PathVariable("asurite") String asurite) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            List<Assignment> completedAssignments = assignmentRepository.findAllByIsCompleteAndAssignedAsurite(true, asurite);
            List<CompletedEvaluation> completedEvaluationList = new ArrayList<CompletedEvaluation>();
            for (Assignment assignment: completedAssignments) {
                // check to see if the assignment's semester has released the evaluations yet
                if (evaluationsReleasedRepository.findOne(assignment.getSemesterName()).isIsReleased()) {
                    List<Response> responses = responseRepository.findAllByAssignmentIdOrderByQuestionIdAsc(assignment.getAssignmentId());
                    if (responses.isEmpty()) {
                        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                    if (responses.get(0).isIsShared()) {
                        CompletedEvaluation completedEvaluation = new CompletedEvaluation();
                        completedEvaluation.setEvalType(assignment.getEvalType().name());
                        Staff evaluator = tmsApiHelper.getStaffByAsuriteAndSemester(assignment.getAsurite(), assignment.getSemesterName());
                        Staff evaluatee = tmsApiHelper.getStaffByAsuriteAndSemester(assignment.getAssignedAsurite(), assignment.getSemesterName());
                        completedEvaluation.setEvaluator(evaluator.getFname() + " " + evaluator.getLname());
                        completedEvaluation.setEvaluatee(evaluatee.getFname() + " " + evaluatee.getLname());
                        completedEvaluation.setIsShared(responses.get(0).isIsShared());
                        completedEvaluation.setSemester(assignment.getSemesterName());
                        List<QuestionAndResponse> questionsAndResponses = new ArrayList<QuestionAndResponse>();
                        for (Response response : responses) {
                            QuestionAndResponse questionAndResponse = new QuestionAndResponse();
                            QuestionDetails questionDetails = new QuestionDetails();
                            Question question = questionRepository.findOne(response.getQuestionId());
                            questionDetails.setQuestionNumber(question.getQuestionNumber());
                            questionDetails.setQuestionPrompt(question.getQuestionPrompt());
                            questionDetails.setQuestionType(question.getQuestionType().name());
                            questionAndResponse.setQuestion(questionDetails);
                            questionAndResponse.setResponse(response.getResponse());
                            questionsAndResponses.add(questionAndResponse);
                        }
                        completedEvaluation.setQuestionsAndResponses(questionsAndResponses);
                        completedEvaluationList.add(completedEvaluation);
                    }
                }

            }
            return new ResponseEntity<List<CompletedEvaluation>>(completedEvaluationList, HttpStatus.OK);
        }

        return new ResponseEntity<List<CompletedEvaluation>>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<CompletedEvaluation>> getUserEvaluations(@ApiParam(value = "",required=true) @PathVariable("asurite") String asurite) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            List<Assignment> completedAssignments = assignmentRepository.findAllByIsCompleteAndAssignedAsurite(true, asurite);
            List<CompletedEvaluation> completedEvaluationList = new ArrayList<CompletedEvaluation>();
            for (Assignment assignment: completedAssignments) {
                List<Response> responses = responseRepository.findAllByAssignmentIdOrderByQuestionIdAsc(assignment.getAssignmentId());
                if (responses.isEmpty()) {
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
                CompletedEvaluation completedEvaluation = new CompletedEvaluation();
                completedEvaluation.setEvalType(assignment.getEvalType().name());
                Staff evaluator = tmsApiHelper.getStaffByAsuriteAndSemester(assignment.getAsurite(), assignment.getSemesterName());
                Staff evaluatee = tmsApiHelper.getStaffByAsuriteAndSemester(assignment.getAssignedAsurite(), assignment.getSemesterName());
                completedEvaluation.setEvaluator(evaluator.getFname() + " " + evaluator.getLname());
                completedEvaluation.setEvaluatee(evaluatee.getFname() + " " + evaluatee.getLname());
                completedEvaluation.setIsShared(responses.get(0).isIsShared());
                completedEvaluation.setSemester(assignment.getSemesterName());
                List<QuestionAndResponse> questionsAndResponses = new ArrayList<QuestionAndResponse>();
                for (Response response: responses) {
                    QuestionAndResponse questionAndResponse = new QuestionAndResponse();
                    QuestionDetails questionDetails = new QuestionDetails();
                    Question question = questionRepository.findOne(response.getQuestionId());
                    questionDetails.setQuestionNumber(question.getQuestionNumber());
                    questionDetails.setQuestionPrompt(question.getQuestionPrompt());
                    questionDetails.setQuestionType(question.getQuestionType().name());
                    questionAndResponse.setQuestion(questionDetails);
                    questionAndResponse.setResponse(response.getResponse());
                    questionsAndResponses.add(questionAndResponse);
                }
                completedEvaluation.setQuestionsAndResponses(questionsAndResponses);
                completedEvaluationList.add(completedEvaluation);

            }
            return new ResponseEntity<List<CompletedEvaluation>>(completedEvaluationList, HttpStatus.OK);
        }

        return new ResponseEntity<List<CompletedEvaluation>>(HttpStatus.BAD_REQUEST);
    }

}
