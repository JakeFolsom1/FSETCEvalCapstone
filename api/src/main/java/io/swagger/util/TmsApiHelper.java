package io.swagger.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.model.Staff;
import io.swagger.repository.SemesterRepository;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Component
public class TmsApiHelper {

    @Autowired
    SemesterRepository semesterRepository;

    public List<Staff> getAllStaffInCurrentSemester() {
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
            if (responseMap.get("status").equals("OK")) {
                List termsList = (List) responseMap.get("terms");
                List<Map<String, String>> apiStaffList = (List<Map<String, String>>) (((Map)termsList.get(0)).get("staff"));
                List<Staff> staffList = new ArrayList<>();
                for(Map<String, String> staff: apiStaffList) {
                    int sid = Integer.parseInt(staff.get("sid"));
                    staffList.add(new Staff(sid, staff.get("asurite"), staff.get("fname"), staff.get("lname"), staff.get("major"), staff.get("cluster"), staff.get("email"), staff.get("role")));
                }

                return staffList;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Staff getStaffByAsuriteAndSemester(String asurite, String semester) {
        CredentialsProvider provider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("EVAL", "dbbac9ba-feeb-11e9-8f0b-362b9e155667");
        provider.setCredentials(AuthScope.ANY, credentials);
        HttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
        // get the staff for the active term
        String activeSemester = semesterRepository.findByIsActive(true).getSemesterName();
        String url = "https://fsetc.asu.edu/tmsapi/staff?id=" + asurite;
        if (activeSemester.equals(semester) == false) {
            url += "term=" + semester;
        }
        HttpGet request = new HttpGet(url);
        request.setHeader("Accept", "application/json");
        try {
            HttpResponse response = client.execute(request);
            InputStream stream = response.getEntity().getContent();
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> responseMap = mapper.readValue(stream, Map.class);
            if (responseMap.get("status").equals("OK")) {
                List termsList = (List) responseMap.get("terms");
                List<Map<String, String>> apiStaffList = (List<Map<String, String>>) (((Map)termsList.get(0)).get("staff"));
                Map<String, String> staff = apiStaffList.get(0);
                int sid = Integer.parseInt(staff.get("sid"));
                return new Staff(sid, staff.get("asurite"), staff.get("fname"), staff.get("lname"), staff.get("major"), staff.get("cluster"), staff.get("email"), staff.get("role"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Staff getStaffByAsurite(String asurite) {
        return getStaffByAsuriteAndSemester(asurite, semesterRepository.findByIsActive(true).getSemesterName());
    }

    public List<Staff> getStaffOfRoleInCurrentSemester(String role) {
        List<Staff> staffList = getAllStaffInCurrentSemester();
        if (staffList != null) {
            for (int i = 0; i < staffList.size(); i++) {
                if (staffList.get(i).getRole().equals(role) == false) {
                    staffList.remove(i--);
                }
            }
        }
        return staffList;
    }

    public Map<String, Staff> getStaffMap() {
        List<Staff> staffList = getAllStaffInCurrentSemester();
        Map<String, Staff> staffMap = new HashMap<>();
        if (staffList != null) {
            for (Staff staff : staffList) {
                staffMap.put(staff.getAsurite(), staff);
            }
        }
        return staffMap;
    }


//    public Set getAllMajorClusters() {
//        List<Staff> staffList = getAllStaffInCurrentSemester();
//        Set<String> majorClusters = new HashSet<>();
//        if (staffList != null) {
//            for (Staff staff : staffList) {
//                majorClusters.add(staff.getCluster());
//            }
//        }
//        return majorClusters;
//    }

    public Map<String, List<String>> getMapFromClusterToTutors() {
        List<Staff> staffList = getAllStaffInCurrentSemester();
        Map<String, List<String>> clusterToTutors = new HashMap<>();
        if (staffList != null) {
            for (Staff staff : staffList) {
                if (clusterToTutors.containsKey(staff.getCluster()) == false) {
                    clusterToTutors.put(staff.getCluster(), new ArrayList());
                }
                if (staff.getRole().equals("TUTOR")) {
                    clusterToTutors.get(staff.getCluster()).add(staff.getAsurite());
                }
            }
        }
        return clusterToTutors;
    }

//    public List<Staff> getTutorsByMajorCluster(String cluster) {
//        List<Staff> staffList = getAllStaffInCurrentSemester();
//        if (staffList != null) {
//            for (int i = 0; i < staffList.size(); i++) {
//                Staff staff = staffList.get(i);
//                if (staff.getRole().equals("TUTOR") == false || staff.getCluster().equals(cluster) == false) {
//                    staffList.remove(i--);
//                }
//            }
//        }
//        return staffList;
//    }
}
