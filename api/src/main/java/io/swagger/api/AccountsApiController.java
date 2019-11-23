package io.swagger.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiParam;
import io.swagger.model.Account;
import io.swagger.repository.AccountRepository;
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

    public ResponseEntity<List<Account>> getAllActiveTutorsByMajor(@ApiParam(value = "",required=true) @PathVariable("major") String major) {
//        String accept = request.getHeader("Accept");
//        if (accept != null && accept.contains("application/json")) {
//            CredentialsProvider provider = new BasicCredentialsProvider();
//            UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("EVAL", "dbbac9ba-feeb-11e9-8f0b-362b9e155667");
//            provider.setCredentials(AuthScope.ANY, credentials);
//
//            HttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
//
//            List<Account> tutorAccountList = getAllActiveTutors().getBody();
//            for(Account account: tutorAccountList) {
//                HttpGet request = new HttpGet("https://fsetc.asu.edu/ps-data?id=" + account.getAsurite());
//                try {
//                    HttpResponse response = client.execute(request);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }

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
