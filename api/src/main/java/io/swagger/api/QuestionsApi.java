/**
 * NOTE: This class is auto generated by the swagger code generator program (2.4.8).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package io.swagger.api;

import io.swagger.annotations.*;
import io.swagger.model.Question;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-10-25T16:55:34.601Z")

@Api(value = "questions", description = "the questions API")
public interface QuestionsApi {

    @ApiOperation(value = "Set question as active by questionId", nickname = "activateQuestion", notes = "", tags={ "questions", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successfully updated"),
        @ApiResponse(code = 404, message = "Question ID not found") })
    @RequestMapping(value = "/questions/id/{questionId}",
        produces = { "application/json" }, 
        method = RequestMethod.PUT)
    ResponseEntity<Void> activateQuestion(@ApiParam(value = "",required=true) @PathVariable("questionId") Long questionId);


    @ApiOperation(value = "Create a new question", nickname = "createQuestion", notes = "", tags={ "questions", })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Successfully created"),
        @ApiResponse(code = 409, message = "Question already exists")})
    @RequestMapping(value = "/questions",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<Void> createQuestion(@ApiParam(value = "" ,required=true )  @Valid @RequestBody Question body);


    @ApiOperation(value = "Set question as inactive by questionId", nickname = "deactivateQuestion", notes = "", tags={ "questions", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successfully updated"),
        @ApiResponse(code = 404, message = "Question ID not found") })
    @RequestMapping(value = "/questions/id/{questionId}",
        produces = { "application/json" }, 
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deactivateQuestion(@ApiParam(value = "",required=true) @PathVariable("questionId") Long questionId);


    @ApiOperation(value = "Get all questions by evalType", nickname = "getActiveQuestions", notes = "", response = Question.class, responseContainer = "List", tags={ "questions", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Success", response = Question.class, responseContainer = "List"),
        @ApiResponse(code = 404, message = "Questions not found") })
    @RequestMapping(value = "/questions/{evalType}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<List<Question>> getActiveQuestions(@ApiParam(value = "",required=true) @PathVariable("evalType") String evalType);


    @ApiOperation(value = "Get question by questionId", nickname = "getQuestion", notes = "", response = Question.class, tags={ "questions", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Success", response = Question.class),
        @ApiResponse(code = 404, message = "Question ID not found") })
    @RequestMapping(value = "/questions/id/{questionId}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<Question> getQuestion(@ApiParam(value = "",required=true) @PathVariable("questionId") Long questionId);


    @ApiOperation(value = "Update an existing question", nickname = "updateQuestion", notes = "", tags={ "questions", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successfully updated"),
        @ApiResponse(code = 404, message = "Question ID not found") })
    @RequestMapping(value = "/questions",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    ResponseEntity<Void> updateQuestion(@ApiParam(value = "" ,required=true )  @Valid @RequestBody Question body);

}