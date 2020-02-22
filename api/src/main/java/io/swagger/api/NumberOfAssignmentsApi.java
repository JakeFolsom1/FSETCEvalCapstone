package io.swagger.api;

import io.swagger.annotations.*;
import io.swagger.model.Account;
import io.swagger.model.NumberOfAssignments;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Api(value = "numAssignments", description = "the num assignments API")
public interface NumberOfAssignmentsApi {
    @ApiOperation(value = "Create a num assignments", nickname = "createNumAssignments", notes = "", tags={ "numAssignments", })
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created") })
    @RequestMapping(value = "/numAssignments",
            produces = { "application/json" },
            consumes = { "application/json" },
            method = RequestMethod.POST)
    ResponseEntity<Void> createNumAssignments(@ApiParam(value = "" ,required=true )  @Valid @RequestBody NumberOfAssignments body);


    @ApiOperation(value = "Delete a number of assignments by semester name", nickname = "deleteNumAssignments", notes = "", tags={ "numAssignments", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted"),
            @ApiResponse(code = 404, message = "Semester not found") })
    @RequestMapping(value = "/numAssignments/{semesterName}",
            produces = { "application/json" },
            method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteNumAssignments(@ApiParam(value = "",required=true) @PathVariable("semesterName") String semesterName);


    @ApiOperation(value = "Get numAssignments by semesterName", nickname = "getNumAssignments", notes = "", response = NumberOfAssignments.class, tags={ "numAssignments", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = NumberOfAssignments.class),
            @ApiResponse(code = 404, message = "Semester not found") })
    @RequestMapping(value = "/numAssignments/{semesterName}",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<NumberOfAssignments> getNumAssignments(@ApiParam(value = "",required=true) @PathVariable("semesterName") String semesterName);

    @ApiOperation(value = "Update an existing numAssignments", nickname = "updateNumAssignments", notes = "", tags={ "numAssignments", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated"),
            @ApiResponse(code = 404, message = "Semester not found")})
    @RequestMapping(value = "/numAssignments/{semesterName}/{numAssignments}",
            produces = { "application/json" },
            method = RequestMethod.PUT)
    ResponseEntity<Void> updateNumAssignments(@ApiParam(value = "" ,required=true )  @PathVariable("semesterName") String semesterName, @ApiParam(value = "" ,required=true )  @PathVariable("numAssignments") Long numAssignments);
}
