package io.swagger.api;

import io.swagger.annotations.*;
import io.swagger.model.EvaluationsReleased;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Api(value = "evaluationsReleased", description = "the evaluations released API")
public interface EvaluationsReleasedApi {
    @ApiOperation(value = "Create an evaluations released", nickname = "createEvaluationsReleased", notes = "", tags={ "evaluationsReleased", })
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created") })
    @RequestMapping(value = "/evaluationsReleased",
            produces = { "application/json" },
            consumes = { "application/json" },
            method = RequestMethod.POST)
    ResponseEntity<Void> createEvaluationsReleased(@ApiParam(value = "", required = true) @Valid @RequestBody EvaluationsReleased body);


    @ApiOperation(value = "Delete an evaluations released by semester name", nickname = "deleteEvaluationsReleased", notes = "", tags={ "evaluationsReleased", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted"),
            @ApiResponse(code = 404, message = "Semester not found") })
    @RequestMapping(value = "/evaluationsReleased/{semesterName}",
            produces = { "application/json" },
            method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteEvaluationsReleased(@ApiParam(value = "", required = true) @PathVariable("semesterName") String semesterName);


    @ApiOperation(value = "Get evaluationsReleased for the current semester", nickname = "getEvaluationsReleased", notes = "", response = EvaluationsReleased.class, tags={ "evaluationsReleased", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = EvaluationsReleased.class),
            @ApiResponse(code = 404, message = "Semester not found") })
    @RequestMapping(value = "/evaluationsReleased",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<EvaluationsReleased> getEvaluationsReleased();

    @ApiOperation(value = "Update the current semester's evaluationsReleased", nickname = "updateEvaluationsReleased", notes = "", tags={ "evaluationsReleased", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated"),
            @ApiResponse(code = 404, message = "Semester not found")})
    @RequestMapping(value = "/evaluationsReleased/{isReleased}",
            produces = { "application/json" },
            method = RequestMethod.PUT)
    ResponseEntity<Void> updateEvaluationsReleased(@ApiParam(value = "", required = true) @PathVariable("isReleased") Boolean isReleased);
}
