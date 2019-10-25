package io.swagger.api;

import io.swagger.model.Preference;
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
public class PreferencesApiController implements PreferencesApi {

    private static final Logger log = LoggerFactory.getLogger(PreferencesApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public PreferencesApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<Void> createPreference(@ApiParam(value = "Prefrence object that needs to be created in the database" ,required=true )  @Valid @RequestBody Preference body) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> deletePreference(@ApiParam(value = "",required=true) @PathVariable("asurite") String asurite,@ApiParam(value = "",required=true) @PathVariable("preferenceNumber") Long preferenceNumber) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<List<Preference>> getAllPreferences() {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<List<Preference>>(objectMapper.readValue("[ {  \"preferredAsurite\" : \"smurra11\",  \"asurite\" : \"jjbowma2\",  \"preferenceNumber\" : 2}, {  \"preferredAsurite\" : \"smurra11\",  \"asurite\" : \"jjbowma2\",  \"preferenceNumber\" : 2} ]", List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<Preference>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<Preference>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<List<Preference>> getPreferences(@ApiParam(value = "asurite of user",required=true) @PathVariable("asurite") String asurite) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<List<Preference>>(objectMapper.readValue("[ {  \"preferredAsurite\" : \"smurra11\",  \"asurite\" : \"jjbowma2\",  \"preferenceNumber\" : 2}, {  \"preferredAsurite\" : \"smurra11\",  \"asurite\" : \"jjbowma2\",  \"preferenceNumber\" : 2} ]", List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<Preference>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<Preference>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> updatePreference(@ApiParam(value = "Preference object that needs to be updated in the database" ,required=true )  @Valid @RequestBody Preference body) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

}
