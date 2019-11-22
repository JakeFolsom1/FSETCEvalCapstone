/**
 * NOTE: This class is auto generated by the swagger code generator program (2.4.8).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package io.swagger.api;

import io.swagger.model.Account;
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

@Api(value = "accounts", description = "the accounts API")
public interface AccountsApi {

    @ApiOperation(value = "Create an account", nickname = "createAccount", notes = "", tags={ "accounts", })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Successfully created") })
    @RequestMapping(value = "/accounts",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<Void> createAccount(@ApiParam(value = "" ,required=true )  @Valid @RequestBody Account body);


    @ApiOperation(value = "Delete an account by asurite", nickname = "deleteAccount", notes = "", tags={ "accounts", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successfully deleted"),
        @ApiResponse(code = 404, message = "User not found") })
    @RequestMapping(value = "/accounts/{asurite}",
        produces = { "application/json" }, 
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteAccount(@ApiParam(value = "",required=true) @PathVariable("asurite") String asurite);


    @ApiOperation(value = "Get an account by asurite", nickname = "getAccount", notes = "", response = Account.class, tags={ "accounts", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Success", response = Account.class),
        @ApiResponse(code = 404, message = "User not found") })
    @RequestMapping(value = "/accounts/{asurite}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<Account> getAccount(@ApiParam(value = "",required=true) @PathVariable("asurite") String asurite);

    @ApiOperation(value = "Get a majorCluster of an account by asurite", nickname = "getMajorCluster", notes = "", response = String.class, tags={ "accounts", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = String.class),
            @ApiResponse(code = 404, message = "User not found") })
    @RequestMapping(value = "/accounts/major/{asurite}",
            produces = { "application/text" },
            method = RequestMethod.GET)
    ResponseEntity<String> getMajorCluster(@ApiParam(value = "",required=true) @PathVariable("asurite") String asurite);


    @ApiOperation(value = "Get all active accounts", nickname = "getAllActiveAccounts", notes = "", response = Account.class, responseContainer = "List", tags={ "accounts", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Success", response = Account.class, responseContainer = "List") })
    @RequestMapping(value = "/accounts",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<List<Account>> getAllActiveAccounts();

    @ApiOperation(value = "Get all active tutor accounts", nickname = "getAllActiveTutors", notes = "", response = Account.class, responseContainer = "List", tags={ "accounts", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = Account.class, responseContainer = "List") })
    @RequestMapping(value = "/accounts/tutors",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<List<Account>> getAllActiveTutors();

    @ApiOperation(value = "Get all active tutor accounts by major cluster", nickname = "getAllActiveTutorsByMajorCluster", notes = "", response = Account.class, responseContainer = "List", tags={ "accounts", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = Account.class, responseContainer = "List") })
    @RequestMapping(value = "/accounts/tutors/{majorCluster}",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<List<Account>> getAllActiveTutorsByMajorCluster(@ApiParam(value = "",required=true) @PathVariable("majorCluster") String majorCluster);


    @ApiOperation(value = "Update an existing account", nickname = "updateAccount", notes = "", tags={ "accounts", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successfully updated"),
        @ApiResponse(code = 404, message = "User not found")})
    @RequestMapping(value = "/accounts",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    ResponseEntity<Void> updateAccount(@ApiParam(value = "" ,required=true )  @Valid @RequestBody Account body);

}
