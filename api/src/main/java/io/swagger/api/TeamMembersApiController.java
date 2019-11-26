package io.swagger.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiParam;
import io.swagger.model.Account;
import io.swagger.model.TeamMember;
import io.swagger.repository.AccountRepository;
import io.swagger.repository.TeamMemberRepository;
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
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-10-25T16:55:34.601Z")

@Controller
public class TeamMembersApiController implements TeamMembersApi {

    private static final Logger log = LoggerFactory.getLogger(TeamMembersApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    TeamMemberRepository teamMemberRepository;

    @Autowired
    AccountRepository accountRepository;

    @org.springframework.beans.factory.annotation.Autowired
    public TeamMembersApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<Void> createTeamMember(@ApiParam(value = "" ,required=true )  @Valid @RequestBody TeamMember body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            Account tutor = accountRepository.findOne(body.getTutorAsurite());
            Account lead = accountRepository.findOne(body.getLeadAsurite());
            // ensure asurites are existing accounts
            if (tutor == null || lead == null) {
                return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
            }
            // ensure tutor asurite does not currently have a team member entry
            // and tutor asurite belongs to a tutor
            // and lead asurite belongs to a lead
            else if (teamMemberRepository.exists(body.getTutorAsurite())
                || tutor.getAccountType() != Account.AccountType.tutor
                || lead.getAccountType() != Account.AccountType.lead) {
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
            Iterator<TeamMember> teamMemberIterator = teamMemberRepository.findTeamMembersByLeadAsurite(leadAsurite).iterator();

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
            TeamMember teamMemberToDelete = teamMemberRepository.findOne(tutorAsurite);
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
            Iterator<TeamMember> teamMemberIterator = teamMemberRepository.findTeamMembersByLeadAsurite(leadAsurite).iterator();
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
}
