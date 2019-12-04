/**
 * NOTE: This class is auto generated by the swagger code generator program (2.4.9).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package io.swagger.api;

import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-10-25T16:55:34.601Z")

@Api(value = "reminders", description = "the reminders API")
public interface RemindersApi {

    @ApiOperation(value = "Remind all users with incomplete evaluations", nickname = "remindAll", notes = "", tags={ "reminders", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successfully reminded") })
    @RequestMapping(value = "/reminders",
        method = RequestMethod.POST)
    ResponseEntity<Void> remindAll();


    @ApiOperation(value = "Remind user by asurite to complete their evaluations", nickname = "remindUser", notes = "", tags={ "reminders", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successfully reminded") })
    @RequestMapping(value = "/reminders/{asurite}",
        method = RequestMethod.POST)
    ResponseEntity<Void> remindUser(@ApiParam(value = "", required = true) @PathVariable("asurite") String asurite);

}