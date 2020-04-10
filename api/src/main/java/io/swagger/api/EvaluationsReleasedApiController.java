package io.swagger.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiParam;
import io.swagger.model.EvaluationsReleased;
import io.swagger.model.Semester;
import io.swagger.repository.EvaluationsReleasedRepository;
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

@Controller
public class EvaluationsReleasedApiController implements EvaluationsReleasedApi {

    private static final Logger log = LoggerFactory.getLogger(NumberOfAssignmentsApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    private EvaluationsReleasedRepository evaluationsReleasedRepository;

    @Autowired
    private SemesterRepository semesterRepository;

    @org.springframework.beans.factory.annotation.Autowired
    public EvaluationsReleasedApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    @Override
    public ResponseEntity<Void> createEvaluationsReleased(@ApiParam(value = "", required = true) @Valid @RequestBody EvaluationsReleased body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            if (evaluationsReleasedRepository.findOne(body.getSemesterName()) != null) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            evaluationsReleasedRepository.save(body);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<Void> deleteEvaluationsReleased(@ApiParam(value = "", required = true) @PathVariable("semesterName") String semesterName) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            EvaluationsReleased evaluationsReleased = evaluationsReleasedRepository.findOne(semesterName);
            if (evaluationsReleased == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            evaluationsReleasedRepository.delete(evaluationsReleased);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<EvaluationsReleased> getEvaluationsReleased() {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            Semester activeSemester = semesterRepository.findByIsActive(true);
            if (activeSemester == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            EvaluationsReleased evaluationsReleased = evaluationsReleasedRepository.findOne(activeSemester.getSemesterName());
            if (evaluationsReleased == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(evaluationsReleased, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<Void> updateEvaluationsReleased(@ApiParam(value = "", required = true) @PathVariable("isReleased") Boolean isReleased) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            Semester activeSemester = semesterRepository.findByIsActive(true);
            if (activeSemester == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            EvaluationsReleased evaluationsReleased = evaluationsReleasedRepository.findOne(activeSemester.getSemesterName());
            if (evaluationsReleased == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            evaluationsReleased.setIsReleased(isReleased);
            evaluationsReleasedRepository.save(evaluationsReleased);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
