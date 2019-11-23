/**
 * NOTE: This class is auto generated by the swagger code generator program (2.4.8).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package io.swagger.api;

import io.swagger.model.Assignment;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-10-25T16:55:34.601Z")

@Api(value = "assignments", description = "the assignments API")
public interface AssignmentsApi {

    @ApiOperation(value = "Auto-assign tutor by asurite", nickname = "autoAssign", notes = "", tags={ "assignments", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successfully auto-assigned tutor"),
        @ApiResponse(code = 409, message = "Could not auto-assign. No solution found.") })
    @RequestMapping(value = "/assignments/auto/{asurite}",
        produces = { "application/json" }, 
        method = RequestMethod.POST)
    ResponseEntity<Void> autoAssign(@ApiParam(value = "",required=true) @PathVariable("asurite") String asurite);


    @ApiOperation(value = "Auto-assign all tutors", nickname = "autoAssignAll", notes = "", tags={ "assignments", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successfully auto-assigned all"),
        @ApiResponse(code = 409, message = "Could not auto-assign all. No solution found.") })
    @RequestMapping(value = "/assignments/auto",
        produces = { "application/json" }, 
        method = RequestMethod.POST)
    ResponseEntity<Void> autoAssignAll();


    @ApiOperation(value = "Set assignment as complete by assignmentId", nickname = "completeAssignment", notes = "", tags={ "assignments", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successfully updated"),
        @ApiResponse(code = 400, message = "Invalid asurite supplied"),
        @ApiResponse(code = 404, message = "User not found") })
    @RequestMapping(value = "/assignments/id/{assignmentId}",
        produces = { "application/json" }, 
        method = RequestMethod.PUT)
    ResponseEntity<Void> completeAssignment(@ApiParam(value = "",required=true) @PathVariable("assignmentId") Long assignmentId);


    @ApiOperation(value = "Create a new assignment", nickname = "createAssignment", notes = "", tags={ "assignments", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successfully created"),
        @ApiResponse(code = 405, message = "Invalid input") })
    @RequestMapping(value = "/assignments",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<Void> createAssignment(@ApiParam(value = "" ,required=true )  @Valid @RequestBody Assignment body);


    @ApiOperation(value = "Delete assignment by assignmentId", nickname = "deleteAssignment", notes = "", tags={ "assignments", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successfully deleted"),
        @ApiResponse(code = 400, message = "Invalid asurite supplied"),
        @ApiResponse(code = 404, message = "User not found") })
    @RequestMapping(value = "/assignments/id/{assignmentId}",
        produces = { "application/json" }, 
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteAssignment(@ApiParam(value = "",required=true) @PathVariable("assignmentId") Long assignmentId);


    @ApiOperation(value = "Get all assignments", nickname = "getAllAssignments", notes = "", response = Assignment.class, responseContainer = "List", tags={ "assignments", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Success", response = Assignment.class, responseContainer = "List") })
    @RequestMapping(value = "/assignments",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<List<Assignment>> getAllAssignments();


    @ApiOperation(value = "Get all assignments for a user by asurite", nickname = "getAllUserAssignments", notes = "", response = Assignment.class, responseContainer = "List", tags={ "assignments", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Success", response = Assignment.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Invalid asurite supplied"),
        @ApiResponse(code = 404, message = "User not found") })
    @RequestMapping(value = "/assignments/{asurite}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<List<Assignment>> getAllUserAssignments(@ApiParam(value = "",required=true) @PathVariable("asurite") String asurite);

    @ApiOperation(value = "Get all assignments for a user by asurite for the active semester", nickname = "getActiveUserAssignments", notes = "", response = Assignment.class, responseContainer = "List", tags={ "assignments", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = Assignment.class, responseContainer = "List"),
            @ApiResponse(code = 400, message = "Invalid asurite supplied"),
            @ApiResponse(code = 404, message = "User not found") })
    @RequestMapping(value = "/assignments/active/{asurite}",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<List<Assignment>> getActiveUserAssignments(@ApiParam(value = "",required=true) @PathVariable("asurite") String asurite);


    @ApiOperation(value = "Update an existing assignment", nickname = "updateAssignment", notes = "", tags={ "assignments", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successfully updated"),
        @ApiResponse(code = 400, message = "Invalid asurite(s) supplied"),
        @ApiResponse(code = 404, message = "Assignment not found"),
        @ApiResponse(code = 405, message = "Invalid input") })
    @RequestMapping(value = "/assignments",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    ResponseEntity<Void> updateAssignment(@ApiParam(value = "" ,required=true )  @Valid @RequestBody Assignment body);

}
