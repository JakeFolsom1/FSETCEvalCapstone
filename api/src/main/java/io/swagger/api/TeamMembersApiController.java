package io.swagger.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiParam;
import io.swagger.model.Semester;
import io.swagger.model.Staff;
import io.swagger.model.TeamMember;
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
    TmsApiHelper tmsApiHelper;

    @org.springframework.beans.factory.annotation.Autowired
    public TeamMembersApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

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
                return new ResponseEntity<Void>(HttpStatus.CREATED);
            }
        }
        return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Void> deleteLeadTeam(@ApiParam(value = "",required=true) @PathVariable("leadAsurite") String leadAsurite) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            String activeSemesterName = semesterRepository.findByIsActive(true).getSemesterName();
            Iterator<TeamMember> teamMemberIterator = teamMemberRepository.findTeamMembersByLeadAsuriteAndSemesterName(leadAsurite, activeSemesterName).iterator();

            // if no teamMembers were found using the leadAsurite
            if (!teamMemberIterator.hasNext()) {
                // return a not found response
                return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
            }
            while (teamMemberIterator.hasNext()) {
                teamMemberRepository.delete(teamMemberIterator.next());
            }
            return new ResponseEntity<Void>(HttpStatus.OK);
        }
        return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Void> deleteTeamMember(@ApiParam(value = "",required=true) @PathVariable("tutorAsurite") String tutorAsurite) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            String activeSemesterName = semesterRepository.findByIsActive(true).getSemesterName();
            TeamMember teamMemberToDelete = teamMemberRepository.findOne(new TeamMember.TeamMemberPK(tutorAsurite, activeSemesterName));
            if (teamMemberToDelete == null) {
                return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
            }
            teamMemberRepository.delete(teamMemberToDelete);
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
            if (!teamMemberIterator.hasNext()) {
                return new ResponseEntity<List<String>>(HttpStatus.NOT_FOUND);
            }
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
