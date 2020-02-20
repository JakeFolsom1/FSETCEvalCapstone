package io.swagger.api;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiParam;
import io.swagger.model.Account;
import io.swagger.model.Assignment;
import io.swagger.repository.AccountRepository;
import io.swagger.repository.AssignmentRepository;
import io.swagger.repository.SemesterRepository;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
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
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

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

        CredentialsProvider provider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("EVAL", "dbbac9ba-feeb-11e9-8f0b-362b9e155667");
        provider.setCredentials(AuthScope.ANY, credentials);
        HttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
        HttpGet request = new HttpGet("https://fsetc.asu.edu/tmsapi/staff?id=" + asurite);
        request.setHeader("Accept", "application/json");
        try {
            HttpResponse response = client.execute(request);
            InputStream stream = response.getEntity().getContent();
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> responseMap = mapper.readValue(stream, Map.class);
            if (!responseMap.get("status").equals("OK")) {
                return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
            }
            List termsList = (List) responseMap.get("terms");
            List<Map<String, Object>> staffList = (List<Map<String, Object>>) (((Map) termsList.get(0)).get("staff"));
            String tutorEmail = (String) staffList.get(0).get("email");

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(tutorEmail);
            message.setSubject("Reminder: Complete End of Semester Evaluations");
            message.setFrom("tutoring-no-reply@engineering.asu.edu");
            String emailText = "Please complete evaluation(s) for:\n";
            for (Assignment assignment : incompleteAssignments) {
                Account assignedAccount = accountRepository.findOne(assignment.getAssignedAsurite());
                emailText += String.format("\t%s %s\n", (String) staffList.get(0).get("fname"), (String) staffList.get(0).get("lname"));
            }
            message.setText(emailText);
            javaMailSender.send(message);
            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
