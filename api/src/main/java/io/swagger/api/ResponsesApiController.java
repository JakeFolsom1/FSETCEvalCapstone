package io.swagger.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiParam;
import io.swagger.model.Assignment;
import io.swagger.model.Question;
import io.swagger.model.Response;
import io.swagger.repository.AssignmentRepository;
import io.swagger.repository.QuestionRepository;
import io.swagger.repository.ResponseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-11-26T00:23:39.034Z")

@Controller
public class ResponsesApiController implements ResponsesApi {
    private static final Logger log = LoggerFactory.getLogger(ResponsesApiController.class);
    private final ObjectMapper objectMapper;
    private final HttpServletRequest request;
    @Autowired
    private ResponseRepository responseRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AssignmentRepository assignmentRepository;

    @org.springframework.beans.factory.annotation.Autowired
    public ResponsesApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }
    public ResponseEntity<Void> createResponse(@ApiParam(value = "" ,required=true )  @Valid @RequestBody Response body) {
        String accept = request.getHeader("Accept");
        if(accept != null && accept.contains("application/json")) {
            Assignment assignment = assignmentRepository.findOne(body.getAssignmentId());
            Question question = questionRepository.findOne(body.getQuestionId());
            if (assignment == null || question == null) {
                return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
            }
            // ensure no other response exists with primary keys questionId and assignmentId
            // and evalTypes match from assignment and question
            // and the question is active
            else if (responseRepository.findOne(new Response.ResponsePK(body.getQuestionId(), body.getAssignmentId())) != null
                || assignment.getEvalType() != question.getEvalType()
                || !question.isIsActive()) {
                return new ResponseEntity<Void>(HttpStatus.CONFLICT);
            }
            responseRepository.save(body);
            return new ResponseEntity<Void>(HttpStatus.CREATED);
        }
        return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
    }
    public ResponseEntity<Void> deleteQuestionResponse(@ApiParam(value = "",required=true) @PathVariable("assignmentId") Long assignmentId,@ApiParam(value = "",required=true) @PathVariable("questionId") Long questionId) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            Response responseToDelete = responseRepository.findOne(new Response.ResponsePK(questionId, assignmentId));
            if (responseToDelete == null) {
                return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
            }
            responseRepository.delete(responseToDelete);
            return new ResponseEntity<Void>(HttpStatus.OK);
        }
        return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
    }
    public ResponseEntity<List<Response>> getAssignmentResponses(@ApiParam(value = "",required=true) @PathVariable("assignmentId") Long assignmentId) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            List<Response> responseList = new ArrayList<>();
            Iterator<Response> responseIterator = responseRepository.findAll().iterator();
            while(responseIterator.hasNext()) {
                Response response = responseIterator.next();
                if(response.getAssignmentId().equals(assignmentId)){
                    responseList.add(response);
                }
            }
            return new ResponseEntity<List<Response>>(responseList, HttpStatus.OK);
        }
        return new ResponseEntity<List<Response>>(HttpStatus.BAD_REQUEST);
    }
    public ResponseEntity<Response> getQuestionResponse(@ApiParam(value = "",required=true) @PathVariable("assignmentId") Long assignmentId, @ApiParam(value = "",required=true) @PathVariable("questionId") Long questionId) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            Response response = responseRepository.findOne(new Response.ResponsePK(questionId, assignmentId));
            if (response == null) {
                return new ResponseEntity<Response>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<Response>(response, HttpStatus.OK);
        }
        return new ResponseEntity<Response>(HttpStatus.BAD_REQUEST);
    }
    // may be an unnecessary function
    public ResponseEntity<Void> updateQuestionResponse(@ApiParam(value = "" ,required=true )  @Valid @RequestBody Response body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            if (responseRepository.findOne(new Response.ResponsePK(body.getQuestionId(), body.getAssignmentId())) == null) {
                return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
            }
            else {
                Assignment assignment = assignmentRepository.findOne(body.getAssignmentId());
                Question question = questionRepository.findOne(body.getQuestionId());
                if (assignment == null || question == null) {
                    return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
                }
                // ensure new evalTypes match from assignment and question
                // and the new question is active
                else if (assignment.getEvalType() != question.getEvalType()
                        || !question.isIsActive()) {
                    return new ResponseEntity<Void>(HttpStatus.CONFLICT);
                }
                responseRepository.save(body);
                return new ResponseEntity<Void>(HttpStatus.OK);
            }
        }
        return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
    }
}