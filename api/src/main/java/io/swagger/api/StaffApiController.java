package io.swagger.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiParam;
import io.swagger.model.Staff;
import io.swagger.repository.SemesterRepository;
import io.swagger.util.TmsApiHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-10-25T16:55:34.601Z")

@Controller
public class StaffApiController implements StaffApi {

    private static final Logger log = LoggerFactory.getLogger(StaffApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    private SemesterRepository semesterRepository;

    @Autowired
    private TmsApiHelper tmsApiHelper;

    @org.springframework.beans.factory.annotation.Autowired
    public StaffApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<Staff> getStaff(@ApiParam(value = "",required=true) @PathVariable("asurite") String asurite) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            Staff staff = tmsApiHelper.getStaffByAsurite(asurite);
            if (staff == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            else {
                return new ResponseEntity<>(staff, HttpStatus.OK);
            }
        }

        return new ResponseEntity<Staff>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> getMajorCluster(@ApiParam(value = "",required=true) @PathVariable("asurite") String asurite) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/text")) {
            Staff staff = tmsApiHelper.getStaffByAsurite(asurite);
            if (staff == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            else {
                return new ResponseEntity<>(staff.getCluster(), HttpStatus.OK);
            }
        }

        return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Staff>> getAllActiveStaff() {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            List<Staff> staffList = tmsApiHelper.getAllStaffInCurrentSemester();
            if (staffList == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            else {
                return new ResponseEntity<>(staffList, HttpStatus.OK);
            }
        }

        return new ResponseEntity<List<Staff>>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Staff>> getAllActiveTutors() {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            List<Staff> staffList = tmsApiHelper.getStaffOfRoleInCurrentSemester("TUTOR");
            if (staffList == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            else {
                return new ResponseEntity<List<Staff>>(staffList, HttpStatus.OK);
            }
        }
        return new ResponseEntity<List<Staff>>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Staff>> getAllActiveLeads() {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            List<Staff> staffList = tmsApiHelper.getStaffOfRoleInCurrentSemester("LEAD");
            if (staffList == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            else {
                return new ResponseEntity<List<Staff>>(staffList, HttpStatus.OK);
            }
        }
        return new ResponseEntity<List<Staff>>(HttpStatus.BAD_REQUEST);
    }


    public ResponseEntity<List<Staff>> getAllActiveTutorsByMajorCluster(@ApiParam(value = "",required=true) @PathVariable("majorCluster") String majorCluster) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            List<Staff> activeStaffList = new ArrayList<Staff>();
            List<Staff> staffList = tmsApiHelper.getAllStaffInCurrentSemester();
            if (staffList == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            else {
                Iterator<Staff> staffIterator = staffList.iterator();
                while (staffIterator.hasNext()) {
                    Staff staff = staffIterator.next();
                    if (staff.getRole().equals("TUTOR") && staff.getCluster().equals(majorCluster)) {
                        activeStaffList.add(staff);
                    }
                }
            }
            return new ResponseEntity<List<Staff>>(activeStaffList, HttpStatus.OK);
        }

        return new ResponseEntity<List<Staff>>(HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<Map<String, String>> getNames() {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            List<Staff> staffList = tmsApiHelper.getAllStaffInCurrentSemester();
            if (staffList == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                Map<String, String> nameList = new HashMap<>();
                for (Staff staff : staffList) {
                    nameList.put(staff.getAsurite(), staff.getFname() + " " + staff.getLname());
                }
                return new ResponseEntity<Map<String, String>>(nameList, HttpStatus.OK);
            }
        }
        return new ResponseEntity<Map<String, String>>(HttpStatus.BAD_REQUEST);
    }
}
