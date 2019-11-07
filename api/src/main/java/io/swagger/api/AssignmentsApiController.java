package io.swagger.api;

import io.swagger.model.Assignment;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-10-25T16:55:34.601Z")

@Controller
public class AssignmentsApiController implements AssignmentsApi {

    private static final Logger log = LoggerFactory.getLogger(AssignmentsApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

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

    public ResponseEntity<Void> completeAssignment(@ApiParam(value = "",required=true) @PathVariable("assignmentId") Long assignmentId) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> createAssignment(@ApiParam(value = "" ,required=true )  @Valid @RequestBody Assignment body) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> deleteAssignment(@ApiParam(value = "",required=true) @PathVariable("assignmentId") Long assignmentId) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<List<Assignment>> getAllAssignments() {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<List<Assignment>>(objectMapper.readValue("[ {  \"assignedAsurite\" : \"smurra11\",  \"evalType\" : \"p2p\",  \"asurite\" : \"jjbowma2\",  \"semester\" : \"Fall 2019\",  \"assignmentId\" : 54,  \"isComplete\" : false}, {  \"assignedAsurite\" : \"smurra11\",  \"evalType\" : \"p2p\",  \"asurite\" : \"jjbowma2\",  \"semester\" : \"Fall 2019\",  \"assignmentId\" : 54,  \"isComplete\" : false} ]", List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<Assignment>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<Assignment>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<List<Assignment>> getAllUserAssignments(@ApiParam(value = "",required=true) @PathVariable("asurite") String asurite) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<List<Assignment>>(objectMapper.readValue("[ {  \"assignedAsurite\" : \"smurra11\",  \"evalType\" : \"p2p\",  \"asurite\" : \"jjbowma2\",  \"semester\" : \"Fall 2019\",  \"assignmentId\" : 54,  \"isComplete\" : false}, {  \"assignedAsurite\" : \"smurra11\",  \"evalType\" : \"p2p\",  \"asurite\" : \"jjbowma2\",  \"semester\" : \"Fall 2019\",  \"assignmentId\" : 54,  \"isComplete\" : false} ]", List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<Assignment>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<Assignment>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<List<Assignment>> getActiveUserAssignments(String asurite) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<List<Assignment>>(objectMapper.readValue("[ {  \"assignedAsurite\" : \"smurra11\",  \"evalType\" : \"p2p\",  \"asurite\" : \"jjbowma2\",  \"semester\" : \"Fall 2019\",  \"assignmentId\" : 54,  \"isComplete\" : false}, {  \"assignedAsurite\" : \"smurra11\",  \"evalType\" : \"p2p\",  \"asurite\" : \"jjbowma2\",  \"semester\" : \"Fall 2019\",  \"assignmentId\" : 54,  \"isComplete\" : false} ]", List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<Assignment>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<Assignment>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> updateAssignment(@ApiParam(value = "" ,required=true )  @Valid @RequestBody Assignment body) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

}
