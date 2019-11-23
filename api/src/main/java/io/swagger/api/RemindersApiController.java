package io.swagger.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import io.swagger.model.Account;
import io.swagger.model.Assignment;
import io.swagger.repository.AccountRepository;
import io.swagger.repository.AssignmentRepository;
import io.swagger.repository.SemesterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
public class RemindersApiController implements RemindersApi {

    private static final Logger log = LoggerFactory.getLogger(RemindersApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private SemesterRepository semesterRepository;

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private AccountRepository accountRepository;

    @org.springframework.beans.factory.annotation.Autowired
    public RemindersApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<Void> remindAll() {
        String currentSemester = semesterRepository.findByIsActive(true).getSemesterName();
        List<Assignment> incompleteAssignmentsDistinct = assignmentRepository.findDistinctByIsCompleteAndSemester(false, currentSemester);
        if (incompleteAssignmentsDistinct.size() == 0) {
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
        for (Assignment assignment: incompleteAssignmentsDistinct) {
            // should there be any checks done on the result?
            remindUser(assignment.getAsurite());
        }
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> remindUser(@ApiParam(value = "",required=true) @PathVariable("asurite") String asurite) {
        String currentSemester = semesterRepository.findByIsActive(true).getSemesterName();
        List<Assignment> incompleteAssignments = assignmentRepository.findAllByIsCompleteAndAsuriteAndSemester(false, asurite, currentSemester);
        if (incompleteAssignments.size() == 0) {
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }

        SimpleMailMessage message = new SimpleMailMessage();
        String tutorEmail = ""; // call tms service here
        message.setTo(tutorEmail);
        message.setSubject("Reminder: Complete End of Semester Evaluations");
        String emailText = "Please complete evaluation(s) for:\n";
        for (Assignment assignment: incompleteAssignments) {
            Account assignedAccount = accountRepository.findOne(assignment.getAssignedAsurite());
            emailText += String.format("\t%s %s\n", assignedAccount.getFirstName(), assignedAccount.getLastName());
        }
        message.setText(emailText);
        javaMailSender.send(message);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
