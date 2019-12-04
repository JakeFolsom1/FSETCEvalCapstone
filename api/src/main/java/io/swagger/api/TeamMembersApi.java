/**
 * NOTE: This class is auto generated by the swagger code generator program (2.4.8).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package io.swagger.api;

import io.swagger.annotations.*;
import io.swagger.model.TeamMember;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-10-25T16:55:34.601Z")

@Api(value = "teamMembers", description = "the teamMembers API")
public interface TeamMembersApi {

    @ApiOperation(value = "Create a teamMember", nickname = "createTeamMember", notes = "", tags={ "teamMembers", })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Successfully created"),
        @ApiResponse(code = 409, message = "TeamMember already exists")})
    @RequestMapping(value = "/teamMembers",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<Void> createTeamMember(@ApiParam(value = "" ,required=true )  @Valid @RequestBody TeamMember body);


    @ApiOperation(value = "Delete all teamMember's of a lead by leadAsurite", nickname = "deleteLeadTeam", notes = "", tags={ "teamMembers", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successfully deleted"),
        @ApiResponse(code = 404, message = "User not found") })
    @RequestMapping(value = "/teamMembers/{leadAsurite}",
        produces = { "application/json" }, 
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteLeadTeam(@ApiParam(value = "",required=true) @PathVariable("leadAsurite") String leadAsurite);


    @ApiOperation(value = "Delete a team member by tutor's asurite", nickname = "deleteTeamMember", notes = "", tags={ "teamMembers", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successfully deleted"),
        @ApiResponse(code = 404, message = "User not found") })
    @RequestMapping(value = "/teamMembers/tutor/{tutorAsurite}",
        produces = { "application/json" }, 
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteTeamMember(@ApiParam(value = "",required=true) @PathVariable("tutorAsurite") String tutorAsurite);


    @ApiOperation(value = "Get a list of teamMembers by lead's asurite", nickname = "getTeamMembers", notes = "", response = String.class, responseContainer = "List", tags={ "teamMembers", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Success", response = String.class, responseContainer = "List"),
        @ApiResponse(code = 404, message = "User not found") })
    @RequestMapping(value = "/teamMembers/{leadAsurite}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<List<String>> getTeamMembers(@ApiParam(value = "",required=true) @PathVariable("leadAsurite") String leadAsurite);
}