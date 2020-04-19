package io.swagger.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiParam;
import io.swagger.model.*;
import io.swagger.repository.AssignmentRepository;
import io.swagger.repository.SemesterRepository;
import io.swagger.repository.TeamMemberRepository;
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
public class TeamMembersApiController implements TeamMembersApi {

    private static final Logger log = LoggerFactory.getLogger(TeamMembersApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    TeamMemberRepository teamMemberRepository;

    @Autowired
    SemesterRepository semesterRepository;

    @Autowired
    AssignmentRepository assignmentRepository;

    @Autowired
    TmsApiHelper tmsApiHelper;

    @org.springframework.beans.factory.annotation.Autowired
    public TeamMembersApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    @Transactional
    public ResponseEntity<Void> createTeamMember(@ApiParam(value = "" ,required=true )  @Valid @RequestBody TeamMember body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            Staff tutor = tmsApiHelper.getStaffByAsurite(body.getTutorAsurite());
            Staff lead = tmsApiHelper.getStaffByAsurite(body.getLeadAsurite());
            // ensure asurites are existing accounts
            if (tutor == null || lead == null) {
                return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
            }
            // ensure tutor asurite does not currently have a team member entry
            // and tutor asurite belongs to a tutor
            // and lead asurite belongs to a lead
            else if (teamMemberRepository.findOne(new TeamMember.TeamMemberPK(body.getTutorAsurite(), body.getSemesterName())) != null
                || tutor.getRole().equals("TUTOR") == false
                || lead.getRole().equals("LEAD") == false) {
                return new ResponseEntity<Void>(HttpStatus.CONFLICT);
            }
            else {
                teamMemberRepository.save(body);
                Assignment t2lAssignment = new Assignment();
                t2lAssignment.setIsComplete(false);
                t2lAssignment.setAssignedAsurite(body.getLeadAsurite());
                t2lAssignment.setAsurite(body.getTutorAsurite());
                t2lAssignment.setSemesterName(body.getSemesterName());
                t2lAssignment.setEvalType(Question.EvalType.t2l);
                assignmentRepository.save(t2lAssignment);
                Assignment l2tAssignment = new Assignment();
                l2tAssignment.setIsComplete(false);
                l2tAssignment.setAsurite(body.getLeadAsurite());
                l2tAssignment.setAssignedAsurite(body.getTutorAsurite());
                l2tAssignment.setSemesterName(body.getSemesterName());
                l2tAssignment.setEvalType(Question.EvalType.l2t);
                assignmentRepository.save(l2tAssignment);
                return new ResponseEntity<Void>(HttpStatus.CREATED);
            }
        }
        return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
    }

    @Transactional
    public ResponseEntity<Void> deleteLeadTeam(@ApiParam(value = "",required=true) @PathVariable("leadAsurite") String leadAsurite) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            String activeSemesterName = semesterRepository.findByIsActive(true).getSemesterName();
            Iterator<TeamMember> teamMemberIterator = teamMemberRepository.findTeamMembersByLeadAsuriteAndSemesterName(leadAsurite, activeSemesterName).iterator();
            while (teamMemberIterator.hasNext()) {
                TeamMember teamMember = teamMemberIterator.next();
                teamMemberRepository.delete(teamMember);
                assignmentRepository.deleteAssignmentByAssignedAsuriteAndAsuriteAndSemesterName(teamMember.getLeadAsurite(), teamMember.getTutorAsurite(), teamMember.getSemesterName());
                assignmentRepository.deleteAssignmentByAssignedAsuriteAndAsuriteAndSemesterName(teamMember.getTutorAsurite(), teamMember.getLeadAsurite(), teamMember.getSemesterName());
            }
            return new ResponseEntity<Void>(HttpStatus.OK);
        }
        return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
    }

    @Transactional
    public ResponseEntity<Void> deleteTeamMember(@ApiParam(value = "",required=true) @PathVariable("tutorAsurite") String tutorAsurite) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            String activeSemesterName = semesterRepository.findByIsActive(true).getSemesterName();
            TeamMember teamMemberToDelete = teamMemberRepository.findOne(new TeamMember.TeamMemberPK(tutorAsurite, activeSemesterName));
            if (teamMemberToDelete == null) {
                return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
            }
            teamMemberRepository.delete(teamMemberToDelete);
            assignmentRepository.deleteAssignmentByAssignedAsuriteAndAsuriteAndSemesterName(teamMemberToDelete.getLeadAsurite(), teamMemberToDelete.getTutorAsurite(), teamMemberToDelete.getSemesterName());
            assignmentRepository.deleteAssignmentByAssignedAsuriteAndAsuriteAndSemesterName(teamMemberToDelete.getTutorAsurite(), teamMemberToDelete.getLeadAsurite(), teamMemberToDelete.getSemesterName());
            return new ResponseEntity<Void>(HttpStatus.OK);
        }
        return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<String>> getTeamMembers(@ApiParam(value = "",required=true) @PathVariable("leadAsurite") String leadAsurite) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            List<String> teamMemberList = new ArrayList<String>();
            String activeSemesterName = semesterRepository.findByIsActive(true).getSemesterName();
            Iterator<TeamMember> teamMemberIterator = teamMemberRepository.findTeamMembersByLeadAsuriteAndSemesterName(leadAsurite, activeSemesterName).iterator();
            while (teamMemberIterator.hasNext()) {
                teamMemberList.add(teamMemberIterator.next().getTutorAsurite());
            }
            return new ResponseEntity<List<String>>(teamMemberList, HttpStatus.OK);
        }
        return new ResponseEntity<List<String>>(HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<Map<String, List<String>>> getAllTeams() {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            Semester currentSemester = semesterRepository.findByIsActive(true);
            if (currentSemester == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            Map<String, List<String>> teamMemberLists = new HashMap<String, List<String>>();
            Iterator<Staff> staffIterator = tmsApiHelper.getStaffOfRoleInCurrentSemester("LEAD").iterator();
            while (staffIterator.hasNext()) {
                String leadAsurite = staffIterator.next().getAsurite();
                Iterator<TeamMember> teamMemberIterator = teamMemberRepository.findTeamMembersByLeadAsuriteAndSemesterName(leadAsurite, currentSemester.getSemesterName()).iterator();
                List<String> teamMemberList = new ArrayList<String>();
                while (teamMemberIterator.hasNext()) {
                    teamMemberList.add(teamMemberIterator.next().getTutorAsurite());
                }
                teamMemberLists.put(leadAsurite, teamMemberList);
            }

            return new ResponseEntity<Map<String, List<String>>>(teamMemberLists, HttpStatus.OK);
        }
        return new ResponseEntity<Map<String, List<String>>>(HttpStatus.BAD_REQUEST);
    }
}
