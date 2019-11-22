package io.swagger.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiParam;
import io.swagger.model.Account;
import io.swagger.repository.AccountRepository;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-10-25T16:55:34.601Z")

@Controller
public class AccountsApiController implements AccountsApi {

    private static final Logger log = LoggerFactory.getLogger(AccountsApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    private AccountRepository accountRepository;

    @org.springframework.beans.factory.annotation.Autowired
    public AccountsApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<Void> createAccount(@ApiParam(value = "" ,required=true )  @Valid @RequestBody Account body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            if (accountRepository.exists(body.getAsurite())) {
                return new ResponseEntity<Void>(HttpStatus.CONFLICT);
            }
            else {
                accountRepository.save(body);
                return new ResponseEntity<Void>(HttpStatus.CREATED);
            }
        }
        return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Void> deleteAccount(@ApiParam(value = "",required=true) @PathVariable("asurite") String asurite) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            if (accountRepository.findOne(asurite) == null) {
                return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
            }
            else {
                accountRepository.delete(asurite);
            }
        }
        return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Account> getAccount(@ApiParam(value = "",required=true) @PathVariable("asurite") String asurite) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            Account account = accountRepository.findOne(asurite);
            if (account == null) {
                return new ResponseEntity<Account>(HttpStatus.NOT_FOUND);
            }
            else {
                return new ResponseEntity<Account>(account, HttpStatus.OK);
            }
        }

        return new ResponseEntity<Account>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> getMajorCluster(@ApiParam(value = "",required=true) @PathVariable("asurite") String asurite) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/text")) {
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
                    return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
                }
                List termsList = (List) responseMap.get("terms");
                List<Map<String, Object>> staffList = (List<Map<String, Object>>) (((Map)termsList.get(0)).get("staff"));
                String majorCluster = (String) staffList.get(0).get("cluster");
                return new ResponseEntity<String>(objectMapper.readValue("\"" + majorCluster + "\"", String.class), HttpStatus.OK);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Account>> getAllActiveAccounts() {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            List<Account> activeAccountList = new ArrayList<Account>();
            Iterator<Account> accountIterator = accountRepository.findAccountsByIsActive(true).iterator();
            while(accountIterator.hasNext())
            {
                activeAccountList.add(accountIterator.next());
            }
            return new ResponseEntity<List<Account>>(activeAccountList, HttpStatus.OK);
        }

        return new ResponseEntity<List<Account>>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Account>> getAllActiveTutors() {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            List<Account> activeAccountList = new ArrayList<Account>();
            Iterator<Account> accountIterator = accountRepository.findAccountsByAccountType("tutor").iterator();
            while(accountIterator.hasNext())
            {
                activeAccountList.add(accountIterator.next());
            }
            return new ResponseEntity<List<Account>>(activeAccountList, HttpStatus.OK);
        }

        return new ResponseEntity<List<Account>>(HttpStatus.BAD_REQUEST);
    }

    // this will only work for the current semester, otherwise we'd need to map our semester names to Charles' term names
    public ResponseEntity<List<Account>> getAllActiveTutorsByMajorCluster(@ApiParam(value = "",required=true) @PathVariable("majorCluster") String majorCluster) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            CredentialsProvider provider = new BasicCredentialsProvider();
            UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("EVAL", "dbbac9ba-feeb-11e9-8f0b-362b9e155667");
            provider.setCredentials(AuthScope.ANY, credentials);
            HttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
            // get the staff for the active term
            HttpGet request = new HttpGet("https://fsetc.asu.edu/tmsapi/staff");
            request.setHeader("Accept", "application/json");
            try {
                HttpResponse response = client.execute(request);
                InputStream stream = response.getEntity().getContent();
                ObjectMapper mapper = new ObjectMapper();
                Map<String, Object> responseMap = mapper.readValue(stream, Map.class);
                if (!responseMap.get("status").equals("OK")) {
                    return new ResponseEntity<List<Account>>(HttpStatus.NOT_FOUND);
                }
                List termsList = (List) responseMap.get("terms");
                List<Map<String, Object>> staffList = (List<Map<String, Object>>) (((Map)termsList.get(0)).get("staff"));
                List<Account> tutorList = new ArrayList<Account>();
                for (Map<String, Object> staff : staffList) {
                    if(staff.get("cluster").equals(majorCluster)) {
                        Account tutor = accountRepository.findOne((String) staff.get("asurite"));
                        if (tutor != null && tutor.getAccountType() == "tutor") {
                            tutorList.add(tutor);
                        }
                    }
                }
                return new ResponseEntity<List<Account>>(tutorList, HttpStatus.OK);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return new ResponseEntity<List<Account>>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Void> updateAccount(@ApiParam(value = "" ,required=true )  @Valid @RequestBody Account body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            if (accountRepository.findOne(body.getAsurite()) == null) {
                return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
            }
            else {
                accountRepository.save(body);
                return new ResponseEntity<Void>(HttpStatus.OK);
            }
        }
        return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
    }

}
