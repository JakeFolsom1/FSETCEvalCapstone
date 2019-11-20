package io.swagger.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiParam;
import io.swagger.model.Assignment;
import io.swagger.repository.AssignmentRepository;
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
public class AssignmentsApiController implements AssignmentsApi {

    private static final Logger log = LoggerFactory.getLogger(AssignmentsApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    private AssignmentRepository assignmentRepository;

    @org.springframework.beans.factory.annotation.Autowired
    public AssignmentsApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<Void> autoAssign(@ApiParam(value = "",required=true) @PathVariable("asurite") String asurite) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> autoAssignAll() {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    /*
     * Can simplify by making an assigment repository function called findAllByAssignmentId to limit the list you'll
     * have to iterate through.
     * Also need to return outside the while to get all of them.
     */
    public ResponseEntity<Void> completeAssignment(@ApiParam(value = "",required=true) @PathVariable("assignmentId") Long assignmentId) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            Iterator<Assignment> accountIterator = assignmentRepository.findAll().iterator();
            while(accountIterator.hasNext())
            {
                Assignment assignment = accountIterator.next();
                if(assignment.getAssignmentId().equals(assignmentId)){
                    assignment.setIsComplete(true);
                    assignmentRepository.save(assignment);
                    return new ResponseEntity<Void>(HttpStatus.OK);
                }
            }
        }
        return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Void> createAssignment(@ApiParam(value = "" ,required=true )  @Valid @RequestBody Assignment body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            if (assignmentRepository.exists(body.getAssignmentId())) {
                return new ResponseEntity<Void>(HttpStatus.CONFLICT);
            }
            else {
                assignmentRepository.save(body);
                return new ResponseEntity<Void>(HttpStatus.CREATED);
            }
        }
        return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
    }

    /*
     * Should return HttpStatus.DELETED
     */
    public ResponseEntity<Void> deleteAssignment(@ApiParam(value = "",required=true) @PathVariable("assignmentId") Long assignmentId) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            if (assignmentRepository.findOne(assignmentId) == null) {
                return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
            }
            else {
                assignmentRepository.delete(assignmentId);
                return new ResponseEntity<Void>(HttpStatus.CREATED);
            }
        }
        return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Assignment>> getAllAssignments() {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            List<Assignment> assignmentList = new ArrayList<Assignment>();
            Iterator<Assignment> accountIterator = assignmentRepository.findAll().iterator();
            while(accountIterator.hasNext())
            {
                Assignment assignment = accountIterator.next();
                assignmentList.add(assignment);
            }
            return new ResponseEntity<List<Assignment>>(assignmentList, HttpStatus.OK);
        }
        return new ResponseEntity<List<Assignment>>(HttpStatus.BAD_REQUEST);
    }

    /*
     * Can simplify by using a new repo function findAllByAsurite
     */
    public ResponseEntity<List<Assignment>> getAllUserAssignments(@ApiParam(value = "",required=true) @PathVariable("asurite") String asurite) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            List<Assignment> assignmentList = new ArrayList<Assignment>();
            Iterator<Assignment> accountIterator = assignmentRepository.findAll().iterator();
            while(accountIterator.hasNext())
            {
                Assignment assignment = accountIterator.next();
                if(assignment.getAsurite().equals(asurite)) {
                    assignmentList.add(assignment);
                }
            }
            return new ResponseEntity<List<Assignment>>(assignmentList, HttpStatus.OK);
        }

        return new ResponseEntity<List<Assignment>>(HttpStatus.BAD_REQUEST);
    }


    public ResponseEntity<List<Assignment>> getActiveUserAssignments(@ApiParam(value = "",required=true) @PathVariable("asurite") String asurite) {
        return null;
    }
//Test
    public ResponseEntity<Void> updateAssignment(@ApiParam(value = "" ,required=true )  @Valid @RequestBody Assignment body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
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
