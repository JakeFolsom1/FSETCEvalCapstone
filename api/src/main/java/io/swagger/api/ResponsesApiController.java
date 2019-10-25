package io.swagger.api;

import io.swagger.model.Response;
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
public class ResponsesApiController implements ResponsesApi {

    private static final Logger log = LoggerFactory.getLogger(ResponsesApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public ResponsesApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<Void> createResponse(@ApiParam(value = "" ,required=true )  @Valid @RequestBody Response body) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> deleteQuestionResponse(@ApiParam(value = "",required=true) @PathVariable("assignmentId") Long assignmentId,@ApiParam(value = "",required=true) @PathVariable("questionId") Long questionId) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<List<Response>> getAssignmentResponses(@ApiParam(value = "",required=true) @PathVariable("assignmentId") Long assignmentId) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<List<Response>>(objectMapper.readValue("[ {  \"questionId\" : 22,  \"response\" : \"John does not wear his sash when he's on shift.\",  \"assignmentId\" : 54,  \"isShared\" : false}, {  \"questionId\" : 22,  \"response\" : \"John does not wear his sash when he's on shift.\",  \"assignmentId\" : 54,  \"isShared\" : false} ]", List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<Response>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<Response>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<List<Response>> getQuestionResponse(@ApiParam(value = "",required=true) @PathVariable("assignmentId") Long assignmentId,@ApiParam(value = "",required=true) @PathVariable("questionId") Long questionId) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<List<Response>>(objectMapper.readValue("[ {  \"questionId\" : 22,  \"response\" : \"John does not wear his sash when he's on shift.\",  \"assignmentId\" : 54,  \"isShared\" : false}, {  \"questionId\" : 22,  \"response\" : \"John does not wear his sash when he's on shift.\",  \"assignmentId\" : 54,  \"isShared\" : false} ]", List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<Response>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<Response>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> updateQuestionResponse(@ApiParam(value = "" ,required=true )  @Valid @RequestBody Response body) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

}
