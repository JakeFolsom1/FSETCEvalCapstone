package io.swagger.api;

import io.swagger.model.Semester;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import io.swagger.repository.SemesterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-10-25T16:55:34.601Z")

@Controller
public class SemestersApiController implements SemestersApi {

    private static final Logger log = LoggerFactory.getLogger(SemestersApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    private SemesterRepository semesterRepository;

    @org.springframework.beans.factory.annotation.Autowired
    public SemestersApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<Void> createSemester(@ApiParam(value = "Semester object that needs to be created in the database" ,required=true )  @Valid @RequestBody Semester body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            if (semesterRepository.exists(body.getSemesterName())) {
                return new ResponseEntity<Void>(HttpStatus.CONFLICT);
            }
            else {
                if (body.isIsActive()) {
                    Semester lastActiveSemester = semesterRepository.findByIsActive(true);
                    if (lastActiveSemester != null) {
                        lastActiveSemester.setIsActive(false);
                        semesterRepository.save(lastActiveSemester);
                    }
                }
                semesterRepository.save(body);
                return new ResponseEntity<Void>(HttpStatus.CREATED);
            }
        }
        return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Void> deleteSemester(@ApiParam(value = "name of the semester to delete from the database",required=true) @PathVariable("semesterName") String semesterName) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            Semester semesterToDelete = semesterRepository.findOne(semesterName);
            if (semesterToDelete == null) {
                return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
            }
            else {
                semesterRepository.delete(semesterToDelete);

                // if this semester was active
                if (semesterToDelete.isIsActive()) {
                    // and there are other semesters left
                    if (semesterRepository.count() > 0) {
                        // activate one of them
                        Semester semesterToActivate = semesterRepository.findAll().iterator().next();
                        semesterToActivate.setIsActive(true);
                        semesterRepository.save(semesterToActivate);
                    }
                }
                return new ResponseEntity<Void>(HttpStatus.OK);
            }
        }
        return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> getActiveSemester() {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/text")) {
            Semester semester = semesterRepository.findByIsActive(true);
            if (semester == null) {
                return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
            }
            else {
                try {
                    return new ResponseEntity<String>(objectMapper.readValue("\"" + semester.getSemesterName() + "\"", String.class), HttpStatus.OK);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Semester>> getAllSemesters() {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            final List<Semester> semesterList = new ArrayList<Semester>();
            Iterator<Semester> semesterIterator = semesterRepository.findAll().iterator();
            while (semesterIterator.hasNext()) {
                semesterList.add(semesterIterator.next());
            }
            return new ResponseEntity<List<Semester>>(semesterList, HttpStatus.OK);
        }
        return new ResponseEntity<List<Semester>>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Void> setActiveSemester(@ApiParam(value = "name of the semester to set as active",required=true) @PathVariable("semesterName") String semesterName) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            Semester lastActiveSemester = semesterRepository.findByIsActive(true);
            Semester newActiveSemester = semesterRepository.findOne(semesterName);
            if (newActiveSemester == null) {
                return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
            }
            else {
                if (lastActiveSemester != null) {
                    lastActiveSemester.setIsActive(false);
                    semesterRepository.save(lastActiveSemester);
                }
                newActiveSemester.setIsActive(true);
                semesterRepository.save(newActiveSemester);
                return new ResponseEntity<Void>(HttpStatus.OK);

            }
        }

        return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
    }

}
