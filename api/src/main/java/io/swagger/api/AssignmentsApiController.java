package io.swagger.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiParam;
import io.swagger.model.*;
import io.swagger.repository.*;
import io.swagger.util.StableMatch;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
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
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-10-25T16:55:34.601Z")

@Controller
public class AssignmentsApiController implements AssignmentsApi {

    private static final Logger log = LoggerFactory.getLogger(AssignmentsApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    private final int NUM_ASSIGNMENTS = 2;

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private SemesterRepository semesterRepository;

    @Autowired
    private PreferenceRepository preferenceRepository;

    @Autowired
    private TeamMemberRepository teamMemberRepository;

    @org.springframework.beans.factory.annotation.Autowired
    public AssignmentsApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    // auto assigns one member their p2p and t2l (tutor) or l2t(lead) assignments
    public ResponseEntity<Void> autoAssign(@ApiParam(value = "",required=true) @PathVariable("asurite") String asurite) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            String activeSemester = semesterRepository.findByIsActive(true).getSemesterName();
            // if asurite belongs to a tutor
            if (accountRepository.findOne(asurite).getAccountType() == Account.AccountType.tutor) {
                // get their preference list
                List<Preference> preferenceList = preferenceRepository.findAllByAsuriteAndSemesterOrderByPreferenceNumber(asurite, activeSemester);

                // generate a list of possible assignments
                List<Assignment> assignmentList = new ArrayList<Assignment>();
                int candidateIndex = 0;
                for (int i = 0; i < NUM_ASSIGNMENTS; i++) {
                    while (candidateIndex < preferenceList.size()) {
                        // get assignment candidate's asurite and increment
                        String candidate = preferenceList.get(candidateIndex++).getPreferredAsurite();

                        // if candidate has not been assigned the total number of assignments, add it to the list and break
                        if (assignmentRepository.findAllByAssignedAsuriteAndSemester(candidate, activeSemester).size() < NUM_ASSIGNMENTS) {
                            Assignment assignment = new Assignment();
                            assignment.setAsurite(asurite);
                            assignment.setAssignedAsurite(candidate);
                            assignment.setEvalType(Question.EvalType.p2p);
                            assignment.setIsComplete(false);
                            assignment.setSemester(activeSemester);
                            assignmentList.add(assignment);
                            break;
                        }
                    }
                }

                // if the desired number of assignments was met
                if (assignmentList.size() == NUM_ASSIGNMENTS) {
                    // save the assignments to the database
                    for (Assignment assignment : assignmentList) {
                        assignmentRepository.save(assignment);
                    }

                    // create and save the lead assignment
                    String lead = teamMemberRepository.findByTutorAsurite(asurite).getLeadAsurite();
                    Assignment leadAssignment = new Assignment();
                    leadAssignment.setSemester(activeSemester);
                    leadAssignment.setIsComplete(false);
                    leadAssignment.setEvalType(Question.EvalType.t2l);
                    leadAssignment.setAssignedAsurite(lead);
                    leadAssignment.setAsurite(asurite);
                    assignmentRepository.save(leadAssignment);
                    return new ResponseEntity<Void>(HttpStatus.OK);
                }
                // desired number could not be met, so return conflict
                else {
                    return new ResponseEntity<Void>(HttpStatus.CONFLICT);
                }
            }
            // if asurite belongs to a lead
            else {
                // get a list of team members
                List<TeamMember> teamMemberList = teamMemberRepository.findTeamMembersByLeadAsurite(asurite);
                // for each team member, create an assignment
                for (TeamMember teamMember: teamMemberList) {
                    Assignment assignment = new Assignment();
                    assignment.setAsurite(asurite);
                    assignment.setAssignedAsurite(teamMember.getTutorAsurite());
                    assignment.setEvalType(Question.EvalType.l2t);
                    assignment.setIsComplete(false);
                    assignment.setSemester(activeSemester);
                    assignmentRepository.save(assignment);
                }
                return new ResponseEntity<Void>(HttpStatus.OK);
            }
        }
        return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Void> autoAssignAll() {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            // clear all current assignments for the semester
            String activeSemester = semesterRepository.findByIsActive(true).getSemesterName();
            assignmentRepository.deleteAllBySemester(activeSemester);

            // assign leads
            List<Account> leadAccountList = accountRepository.findAccountsByAccountTypeAndIsActive(Account.AccountType.lead, true);
            for (Account lead : leadAccountList) {
                // get a list of team members
                List<TeamMember> teamMemberList = teamMemberRepository.findTeamMembersByLeadAsurite(lead.getAsurite());
                // for each team member, create an assignment
                for (TeamMember teamMember: teamMemberList) {
                    Assignment assignment = new Assignment();
                    assignment.setAsurite(teamMember.getLeadAsurite());
                    assignment.setAssignedAsurite(teamMember.getTutorAsurite());
                    assignment.setEvalType(Question.EvalType.l2t);
                    assignment.setIsComplete(false);
                    assignment.setSemester(activeSemester);
                    assignmentRepository.save(assignment);
                }
            }

            // assign tutors
            // for each major Cluster
            Set<String> majorClusters = getAllMajorClusters();
            for (String majorCluster: majorClusters) {
                // get preferences
                Map<String, List<String>> preferenceTable = getPreferenceTableForMajorCluster(majorCluster);
                StableMatch stableMatch = new StableMatch(preferenceTable);
                for (int i = 0; i < NUM_ASSIGNMENTS; i++) {
                    Map<String, String> matches = stableMatch.getMatches();
                    if (matches != null) {
                        for (String evaluator: matches.keySet()) {
                            Assignment assignment = new Assignment();
                            assignment.setAsurite(evaluator);
                            assignment.setAssignedAsurite(matches.get(evaluator));
                            assignment.setEvalType(Question.EvalType.p2p);
                            assignment.setIsComplete(false);
                            assignment.setSemester(activeSemester);
                            assignmentRepository.save(assignment);
                        }
                    }
                }
            }
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
    }

    private Map<String, List<String>> getPreferenceTableForMajorCluster(String majorCluster) {
        Map<String, List<String>> preferenceTable = new HashMap<>();
        String activeSemester = semesterRepository.findByIsActive(true).getSemesterName();
        List<Account> tutors = getActiveTutorsByMajorCluster(majorCluster);
        if (tutors != null) {
            for (Account tutor : tutors) {
                List<Preference> tutorPreferenceList = preferenceRepository.findAllByAsuriteAndSemesterOrderByPreferenceNumber(tutor.getAsurite(), activeSemester);
                List<String> preferenceRow = new ArrayList<>();
                for (Preference preference : tutorPreferenceList) {
                    preferenceRow.add(preference.getPreferredAsurite());
                }
                preferenceTable.put(tutor.getAsurite(), preferenceRow);
            }
        }
        return preferenceTable;
    }

    // not the most elegant option, consider a different approach
    private Set<String> getAllMajorClusters() {
        Set<String> majorClusters = new HashSet<String>();
        CredentialsProvider provider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("EVAL", "dbbac9ba-feeb-11e9-8f0b-362b9e155667");
        provider.setCredentials(AuthScope.ANY, credentials);
        HttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
        HttpGet request = new HttpGet("https://fsetc.asu.edu/tmsapi/staff");
        request.setHeader("Accept", "application/json");
        try {
            HttpResponse response = client.execute(request);
            InputStream stream = response.getEntity().getContent();
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> responseMap = mapper.readValue(stream, Map.class);
            if (!responseMap.get("status").equals("OK")) {
                return null;
            }
            List termsList = (List) responseMap.get("terms");
            List<Map<String, Object>> staffList = (List<Map<String, Object>>) (((Map)termsList.get(0)).get("staff"));
            for (int i = 0; i < staffList.size(); i++) {
                majorClusters.add((String) staffList.get(i).get("cluster"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return majorClusters;
    }

    private List<Account> getActiveTutorsByMajorCluster(String majorCluster) {
        CredentialsProvider provider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("EVAL", "dbbac9ba-feeb-11e9-8f0b-362b9e155667");
        provider.setCredentials(AuthScope.ANY, credentials);
        HttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
        // get the staff for the active term
        HttpGet request = new HttpGet("https://fsetc.asu.edu/tmsapi/staff");
        request.setHeader("Accept", "application/json");
        List<Account> tutorList = new ArrayList<Account>();
        try {
            HttpResponse response = client.execute(request);
            InputStream stream = response.getEntity().getContent();
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> responseMap = mapper.readValue(stream, Map.class);
            if (!responseMap.get("status").equals("OK")) {
                return null;
            }
            List termsList = (List) responseMap.get("terms");
            List<Map<String, Object>> staffList = (List<Map<String, Object>>) (((Map)termsList.get(0)).get("staff"));
            for (Map<String, Object> staff : staffList) {
                if(staff.get("cluster").equals(majorCluster)) {
                    Account tutor = accountRepository.findOne((String) staff.get("asurite"));
                    if (tutor != null && tutor.getAccountType() == Account.AccountType.tutor) {
                        tutorList.add(tutor);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tutorList;
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
            Account evaluator = accountRepository.findOne(body.getAsurite());
            Account evaluatee = accountRepository.findOne(body.getAssignedAsurite());
            Semester semester = semesterRepository.findOne(body.getSemester());
            // ensure assignment has valid asurites and semester name
            if (evaluator == null || evaluatee == null || semester == null) {
                return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
            }
            // ensure no duplicates
            if (assignmentRepository.findByAsuriteAndAssignedAsuriteAndSemester(body.getAsurite(), body.getAssignedAsurite(), body.getSemester()) != null) {
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
                if (assignment.getSemester().equals(activeSemester) && assignment.getAsurite().equals(asurite)) {
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
            Account evaluator = accountRepository.findOne(body.getAsurite());
            Account evaluatee = accountRepository.findOne(body.getAssignedAsurite());
            Semester semester = semesterRepository.findOne(body.getSemester());
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
