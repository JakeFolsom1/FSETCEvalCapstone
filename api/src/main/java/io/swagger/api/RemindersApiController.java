package io.swagger.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiParam;
import io.swagger.model.Assignment;
import io.swagger.model.Semester;
import io.swagger.model.Staff;
import io.swagger.repository.AssignmentRepository;
import io.swagger.repository.SemesterRepository;
import io.swagger.util.TmsApiHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
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
    private TmsApiHelper tmsApiHelper;

    @org.springframework.beans.factory.annotation.Autowired
    public RemindersApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<Void> remindAll() {
        Semester currentSemester = semesterRepository.findByIsActive(true);
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
        Semester currentSemester = semesterRepository.findByIsActive(true);
        List<Assignment> incompleteAssignments = assignmentRepository.findAllByIsCompleteAndAsuriteAndSemester(false, asurite, currentSemester);
        if (incompleteAssignments.size() == 0) {
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }

        Staff staff = tmsApiHelper.getStaffByAsurite(asurite);
        if (staff == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(staff.getEmail());
        message.setSubject("Reminder: Complete End of Semester Evaluations");
        message.setFrom("tutoring-no-reply@engineering.asu.edu");
        String emailText = "Please complete evaluation(s) for:\n";
        for (Assignment assignment : incompleteAssignments) {
            Staff assignedTutor = tmsApiHelper.getStaffByAsurite(assignment.getAssignedAsurite());
            if (staff != null) {
                emailText += String.format("\t%s %s\n", assignedTutor.getFname(), assignedTutor.getLname());
            }
        }
        message.setText(emailText);
        javaMailSender.send(message);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
