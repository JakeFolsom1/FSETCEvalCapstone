package io.swagger.api;

import io.swagger.model.Question;
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
public class QuestionsApiController implements QuestionsApi {

    private static final Logger log = LoggerFactory.getLogger(QuestionsApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public QuestionsApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<Void> activateQuestion(@ApiParam(value = "",required=true) @PathVariable("questionId") Long questionId) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> createQuestion(@ApiParam(value = "" ,required=true )  @Valid @RequestBody Question body) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> deactivateQuestion(@ApiParam(value = "",required=true) @PathVariable("questionId") Long questionId) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<List<Question>> getActiveQuestions(@ApiParam(value = "",required=true) @PathVariable("evalType") String evalType) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<List<Question>>(objectMapper.readValue("[ {  \"evalType\" : \"p2p\",  \"questionId\" : 22,  \"questionPrompt\" : \"Does tutor obey all procedures and policies of the center?\",  \"isActive\" : true,  \"questionNumber\" : 3}, {  \"evalType\" : \"p2p\",  \"questionId\" : 22,  \"questionPrompt\" : \"Does tutor obey all procedures and policies of the center?\",  \"isActive\" : true,  \"questionNumber\" : 3} ]", List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<Question>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<Question>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Question> getQuestion(@ApiParam(value = "",required=true) @PathVariable("questionId") Long questionId) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<Question>(objectMapper.readValue("{  \"evalType\" : \"p2p\",  \"questionId\" : 22,  \"questionPrompt\" : \"Does tutor obey all procedures and policies of the center?\",  \"isActive\" : true,  \"questionNumber\" : 3}", Question.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<Question>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<Question>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> updateQuestion(@ApiParam(value = "" ,required=true )  @Valid @RequestBody Question body) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

}
