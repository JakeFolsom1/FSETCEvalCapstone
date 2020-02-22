package io.swagger.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiParam;
import io.swagger.model.Account;
import io.swagger.model.NumberOfAssignments;
import io.swagger.repository.NumberOfAssignmentsRepository;
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

@Controller
public class NumberOfAssignmentsApiController implements NumberOfAssignmentsApi {

    private static final Logger log = LoggerFactory.getLogger(NumberOfAssignmentsApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    private NumberOfAssignmentsRepository numAssignmentsRepository;

    @org.springframework.beans.factory.annotation.Autowired
    public NumberOfAssignmentsApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    @Override
    public ResponseEntity<Void> createNumAssignments(@ApiParam(value = "" ,required=true )  @Valid @RequestBody NumberOfAssignments body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            if (numAssignmentsRepository.findOne(body.getSemesterName()) != null) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            else {
                numAssignmentsRepository.save(body);
                return new ResponseEntity<>(HttpStatus.CREATED);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<Void> deleteNumAssignments(@ApiParam(value = "",required=true) @PathVariable("semesterName") String semesterName) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            NumberOfAssignments numberOfAssignments = numAssignmentsRepository.findOne(semesterName);
            if (numberOfAssignments == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            else {
                numAssignmentsRepository.delete(numberOfAssignments);
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<NumberOfAssignments> getNumAssignments(@ApiParam(value = "",required=true) @PathVariable("semesterName") String semesterName) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            NumberOfAssignments numberOfAssignments = numAssignmentsRepository.findOne(semesterName);
            if (numberOfAssignments == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            else {
                return new ResponseEntity<>(numberOfAssignments, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<Void> updateNumAssignments(@ApiParam(value = "",required=true) @PathVariable("semesterName") String semesterName, @ApiParam(value = "",required=true) @PathVariable("numAssignments") Long numAssignments) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            NumberOfAssignments numberOfAssignments = numAssignmentsRepository.findOne(semesterName);
            if (numberOfAssignments == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            else {
                numberOfAssignments.setNumAssignments(numAssignments);
                numAssignmentsRepository.save(numberOfAssignments);
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
