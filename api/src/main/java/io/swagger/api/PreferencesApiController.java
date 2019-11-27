package io.swagger.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiParam;
import io.swagger.model.Account;
import io.swagger.model.Preference;
import io.swagger.model.Semester;
import io.swagger.repository.AccountRepository;
import io.swagger.repository.PreferenceRepository;
import io.swagger.repository.SemesterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-10-25T16:55:34.601Z")
@Controller
public class PreferencesApiController implements PreferencesApi {
    private static final Logger log = LoggerFactory.getLogger(PreferencesApiController.class);
    private final ObjectMapper objectMapper;
    private final HttpServletRequest request;
    @Autowired
    private PreferenceRepository preferenceRepository;
    @Autowired
    private SemesterRepository semesterRepository;
    @Autowired
    AccountRepository accountRepository;

    @org.springframework.beans.factory.annotation.Autowired
    public PreferencesApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }
    public ResponseEntity<Void> createPreference(@ApiParam(value = "Prefrence object that needs to be created in the database" ,required=true )  @Valid @RequestBody Preference body) {
        String accept = request.getHeader("Accept");
        if(accept != null && accept.contains("application/json")){
            Account tutor = accountRepository.findOne(body.getAsurite());
            Account preferredTutor = accountRepository.findOne(body.getAsurite());
            Semester semester = semesterRepository.findOne(body.getSemester());
            // ensure asurites are from valid accounts and semester is a valid semester
            if (tutor == null || preferredTutor == null || semester == null) {
                return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
            }
            // ensure preference does not already exist and both asurites are from active tutors
            else if(preferenceRepository.findByAsuriteAndPreferenceNumberAndSemester(body.getAsurite(), body.getPreferenceNumber(), body.getSemester()) != null
                || tutor.getAccountType() != Account.AccountType.tutor
                || preferredTutor.getAccountType() != Account.AccountType.tutor
                || !tutor.isIsActive()
                || !preferredTutor.isIsActive()){
                return new ResponseEntity<Void>(HttpStatus.CONFLICT);
            }else{
                preferenceRepository.save(body);
                return new ResponseEntity<Void>(HttpStatus.CREATED);
            }
        }
        return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Void> deletePreference(@ApiParam(value = "",required=true) @PathVariable("preferenceId") Long preferenceId) {
        String accept = request.getHeader("Accept");
        if(accept != null && accept.contains("application/json")){
            Preference preferenceToDelete = preferenceRepository.findOne(preferenceId);
            if(preferenceToDelete == null){
                return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
            }else {
                preferenceRepository.delete(preferenceToDelete);
                return new ResponseEntity<Void>(HttpStatus.OK);
            }
        }
        return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Preference>> getAllPreferences() {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            String currentSemester = semesterRepository.findByIsActive(true).getSemesterName();
            List<Preference> activePreferenceList = new ArrayList<>();
            Iterator<Preference> preferenceIterator = preferenceRepository.findAll().iterator();
            while(preferenceIterator.hasNext()) {
                Preference temp = preferenceIterator.next();
                if (temp.getSemester().equals(currentSemester)) {
                    activePreferenceList.add(temp);
                }

            }
            return new ResponseEntity<List<Preference>>(activePreferenceList, HttpStatus.OK);
        }
        return new ResponseEntity<List<Preference>>(HttpStatus.BAD_REQUEST);
    }
    public ResponseEntity<List<Preference>> getPreferences(@ApiParam(value = "asurite of user",required=true) @PathVariable("asurite") String asurite) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            String currentSemester = semesterRepository.findByIsActive(true).getSemesterName();
            List<Preference> activePreferenceList = new ArrayList<>();
            Iterator<Preference> preferenceIterator = preferenceRepository.findAll().iterator();
            while(preferenceIterator.hasNext()) {
                Preference preference = preferenceIterator.next();
                String tempAsurite = preference.getAsurite();
                if(tempAsurite.equals(asurite) && preference.getSemester().equals(currentSemester)){
                    activePreferenceList.add(preference);
                }
            }
            return new ResponseEntity<List<Preference>>(activePreferenceList, HttpStatus.OK);
        }
        return new ResponseEntity<List<Preference>>(HttpStatus.BAD_REQUEST);
    }
    public ResponseEntity<Void> updatePreference(@ApiParam(value = "Preference object that needs to be updated in the database" ,required=true )  @Valid @RequestBody Preference body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            if (preferenceRepository.findOne(body.getPreferenceId()) == null) {
                return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
            }
            else {
                Account tutor = accountRepository.findOne(body.getAsurite());
                Account preferredTutor = accountRepository.findOne(body.getAsurite());
                Semester semester = semesterRepository.findOne(body.getSemester());
                // ensure asurites are from valid accounts and semester is a valid semester
                if (tutor == null || preferredTutor == null || semester == null) {
                    return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
                }
                // ensure both asurites are from active tutors
                else if(tutor.getAccountType() != Account.AccountType.tutor
                        || preferredTutor.getAccountType() != Account.AccountType.tutor
                        || !tutor.isIsActive()
                        || !preferredTutor.isIsActive()){
                    return new ResponseEntity<Void>(HttpStatus.CONFLICT);
                }
                preferenceRepository.save(body);
                return new ResponseEntity<Void>(HttpStatus.OK);
            }
        }
        return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
    }
}