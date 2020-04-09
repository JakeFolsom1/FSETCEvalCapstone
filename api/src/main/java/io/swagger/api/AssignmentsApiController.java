package io.swagger.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiParam;
import io.swagger.model.*;
import io.swagger.repository.*;
import io.swagger.util.StableMatch;
import io.swagger.util.TmsApiHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.*;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-10-25T16:55:34.601Z")

@Controller
public class AssignmentsApiController implements AssignmentsApi {

    private static final Logger log = LoggerFactory.getLogger(AssignmentsApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private SemesterRepository semesterRepository;

    @Autowired
    private PreferenceRepository preferenceRepository;

    @Autowired
    private TeamMemberRepository teamMemberRepository;

    @Autowired
    private NumberOfAssignmentsRepository numberOfAssignmentsRepository;

    @Autowired
    private TmsApiHelper tmsApiHelper;

    @org.springframework.beans.factory.annotation.Autowired
    public AssignmentsApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }


    // auto assigns one member their p2p assignments
    public ResponseEntity<Void> autoAssign(@ApiParam(value = "",required=true) @PathVariable("asurite") String asurite) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            Semester activeSemester = semesterRepository.findByIsActive(true);
            Long numAssignments = numberOfAssignmentsRepository.findOne(activeSemester.getSemesterName()).getNumAssignments();
            Map<String, Staff> staffMap = tmsApiHelper.getStaffMap();
            // if asurite belongs to a tutor
            if (staffMap.get(asurite).getRole().equals("TUTOR")) {
                // get their preference list
                List<Preference> preferenceList = preferenceRepository.findAllByAsuriteAndSemesterNameOrderByPreferenceNumber(asurite, activeSemester.getSemesterName());
                List<Assignment> currentAssignmentsList = assignmentRepository.findAllByAsuriteAndSemesterNameAndEvalType(asurite, activeSemester.getSemesterName(), Question.EvalType.p2p);

                // generate a list of possible assignments
                List<Assignment> newAssignmentList = new ArrayList<Assignment>();
                int candidateIndex = 0;
                for (int i = currentAssignmentsList.size(); i < numAssignments; i++) {
                    while (candidateIndex < preferenceList.size()) {
                        // get assignment candidate's asurite and increment
                        String candidate = preferenceList.get(candidateIndex++).getPreferredAsurite();

                        if (assignmentRepository.findByAsuriteAndAssignedAsurite(asurite, candidate) == null) { // if asurite has not already been assigned to candidate
                            if (assignmentRepository.findAllByAssignedAsuriteAndSemesterName(candidate, activeSemester.getSemesterName()).size() < numAssignments) { // and if candidate has not been assigned the total number of assignments, add it to the list and break
                                Assignment assignment = new Assignment();
                                assignment.setAsurite(asurite);
                                assignment.setAssignedAsurite(candidate);
                                assignment.setEvalType(Question.EvalType.p2p);
                                assignment.setIsComplete(false);
                                assignment.setSemesterName(activeSemester.getSemesterName());
                                assignment.setAssignmentNumber(new Long(i + 1));
                                newAssignmentList.add(assignment);
                                break;
                            }
                        }
                    }
                }

                // if the desired number of assignments was met
                if (currentAssignmentsList.size() + newAssignmentList.size() == numAssignments) {
                    // save the assignments to the database
                    for (Assignment assignment : newAssignmentList) {
                        assignmentRepository.save(assignment);
                    }
                    return new ResponseEntity<Void>(HttpStatus.OK);
                }
                // desired number could not be met, so return conflict
                else {
                    return new ResponseEntity<Void>(HttpStatus.CONFLICT);
                }
            }
        }
        return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
    }

    @Transactional
    public ResponseEntity<Void> autoAssignAll() {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            // clear all current assignments for the semester
            Semester activeSemester = semesterRepository.findByIsActive(true);
            Long numAssignments = numberOfAssignmentsRepository.findOne(activeSemester.getSemesterName()).getNumAssignments();
            assignmentRepository.deleteAllBySemesterNameAndEvalType(activeSemester.getSemesterName(), Question.EvalType.p2p);

            // assign tutors
            // for each major Cluster
            Map<String, List<String>> clusterToTutorsMap = tmsApiHelper.getMapFromClusterToTutors();
            for (String majorCluster: clusterToTutorsMap.keySet()) {
                // get preferences for all the students in a major cluster
                Map<String, List<String>> preferenceTable = getPreferenceTableForMajorCluster(clusterToTutorsMap.get(majorCluster));
                StableMatch stableMatch = new StableMatch(preferenceTable);
                for (int i = 0; i < numAssignments; i++) {
                    Map<String, String> matches = stableMatch.getMatches();
                    if (matches != null) {
                        for (String evaluator: matches.keySet()) {
                            Assignment assignment = new Assignment();
                            assignment.setAsurite(evaluator);
                            assignment.setAssignedAsurite(matches.get(evaluator));
                            assignment.setAssignmentNumber(new Long(i + 1));
                            assignment.setEvalType(Question.EvalType.p2p);
                            assignment.setIsComplete(false);
                            assignment.setSemesterName(activeSemester.getSemesterName());
                            assignmentRepository.save(assignment);
                        }
                    }
                }
            }
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
    }

    private Map<String, List<String>> getPreferenceTableForMajorCluster(List<String> tutorsInCluster) {
        Map<String, List<String>> preferenceTable = new HashMap<>();
        Semester activeSemester = semesterRepository.findByIsActive(true);
        for (String tutor : tutorsInCluster) {
            List<Preference> tutorPreferenceList = preferenceRepository.findAllByAsuriteAndSemesterNameOrderByPreferenceNumber(tutor, activeSemester.getSemesterName());
            List<String> preferenceRow = new ArrayList<>();
            for (Preference preference : tutorPreferenceList) {
                preferenceRow.add(preference.getPreferredAsurite());
            }
            preferenceTable.put(tutor, preferenceRow);
        }
        return preferenceTable;
    }

    public ResponseEntity<Void> completeAssignment(@ApiParam(value = "",required=true) @PathVariable("assignmentId") Long assignmentId) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            Assignment assignment = assignmentRepository.findOne(assignmentId);
            if (assignment == null) {
                return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
            }
            assignment.setIsComplete(true);
            assignmentRepository.save(assignment);
            return new ResponseEntity<Void>(HttpStatus.OK);
        }
        return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
    }

    // use an account repository to check if asurite and assigned asurite belong to active accounts
    // use a semester repository to check if semester is valid
    // see preferences create for a similar example
    public ResponseEntity<Void> createAssignment(@ApiParam(value = "" ,required=true )  @Valid @RequestBody Assignment body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            Map<String, Staff> staffList = tmsApiHelper.getStaffMap();
            Staff evaluator = staffList.get(body.getAsurite());
            Staff evaluatee = staffList.get(body.getAssignedAsurite());
            Semester semester = semesterRepository.findOne(body.getSemesterName());
            // ensure assignment has valid asurites and semester name
            if (evaluator == null || evaluatee == null || semester == null) {
                return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
            }
            // ensure no duplicates
            if (assignmentRepository.findByAsuriteAndAssignmentNumberAndEvalTypeAndSemesterName(body.getAsurite(), body.getAssignmentNumber(), body.getEvalType(), body.getSemesterName()) != null) {
                return new ResponseEntity<Void>(HttpStatus.CONFLICT);
            }
            else {
                assignmentRepository.save(body);
                return new ResponseEntity<Void>(HttpStatus.CREATED);
            }
        }
        return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Void> deleteAssignment(@ApiParam(value = "",required=true) @PathVariable("assignmentId") Long assignmentId) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            if (assignmentRepository.findOne(assignmentId) == null) {
                return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
            }
            else {
                assignmentRepository.delete(assignmentId);
                return new ResponseEntity<Void>(HttpStatus.OK);
            }
        }
        return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Assignment>> getAllAssignments() {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            List<Assignment> assignmentList = new ArrayList<Assignment>();
            Iterator<Assignment> assignmentIterator = assignmentRepository.findAll().iterator();
            while(assignmentIterator.hasNext())
            {
                Assignment assignment = assignmentIterator.next();
                assignmentList.add(assignment);
            }
            return new ResponseEntity<List<Assignment>>(assignmentList, HttpStatus.OK);
        }
        return new ResponseEntity<List<Assignment>>(HttpStatus.BAD_REQUEST);
    }


    public ResponseEntity<List<Assignment>> getAllUserAssignments(@ApiParam(value = "",required=true) @PathVariable("asurite") String asurite) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            List<Assignment> assignmentList = new ArrayList<Assignment>();
            Iterator<Assignment> assignmentIterator = assignmentRepository.findAllByAsurite(asurite).iterator();
            while(assignmentIterator.hasNext())
            {
                Assignment assignment = assignmentIterator.next();
                assignmentList.add(assignment);
            }
            return new ResponseEntity<List<Assignment>>(assignmentList, HttpStatus.OK);
        }

        return new ResponseEntity<List<Assignment>>(HttpStatus.BAD_REQUEST);
    }


    public ResponseEntity<List<Assignment>> getActiveUserAssignments(@ApiParam(value = "",required=true) @PathVariable("asurite") String asurite) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            String activeSemester = semesterRepository.findByIsActive(true).getSemesterName();
            List<Assignment> assignmentList = new ArrayList<Assignment>();
            Iterator<Assignment> assignmentIterator = assignmentRepository.findAll().iterator();
            while(assignmentIterator.hasNext()) {
                Assignment assignment = assignmentIterator.next();
                if (assignment.getSemesterName().equals(activeSemester) && assignment.getAsurite().equals(asurite)) {
                    assignmentList.add(assignment);
                }
            }
            return new ResponseEntity<List<Assignment>>(assignmentList, HttpStatus.OK);
        }

        return new ResponseEntity<List<Assignment>>(HttpStatus.BAD_REQUEST);
    }
    public ResponseEntity<Void> updateAssignment(@ApiParam(value = "" ,required=true )  @Valid @RequestBody Assignment body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            Map<String, Staff> staffList = tmsApiHelper.getStaffMap();
            Staff evaluator = staffList.get(body.getAsurite());
            Staff evaluatee = staffList.get(body.getAssignedAsurite());
            Semester semester = semesterRepository.findOne(body.getSemesterName());
            // ensure assignment has valid asurites and semester name
            if (evaluator == null || evaluatee == null || semester == null) {
                return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
            }
            // ensure assignmentId is from existing assignment
            if (assignmentRepository.findOne(body.getAssignmentId()) == null) {
                return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
            }
            else {
                assignmentRepository.save(body);
                return new ResponseEntity<Void>(HttpStatus.OK);
            }
        }
        return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
    }

}
