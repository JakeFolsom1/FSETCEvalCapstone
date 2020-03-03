package io.swagger.model;

import java.util.Objects;

// Simple model to make the TMS API more usable in our codebase
public class Staff {
    private int sid;
    private String asurite;
    private String fname;
    private String lname;
    private String major;
    private String cluster;
    private String email;
    private String role;

    public Staff(int sid, String asurite, String fname, String lname, String major, String cluster, String email, String role) {
        this.sid = sid;
        this.asurite = asurite;
        this.fname = fname;
        this.lname = lname;
        this.major = major;
        this.cluster = cluster;
        this.email = email;
        this.role = role;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getAsurite() {
        return asurite;
    }

    public void setAsurite(String asurite) {
        this.asurite = asurite;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getCluster() {
        return cluster;
    }

    public void setCluster(String cluster) {
        this.cluster = cluster;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Staff)) return false;
        Staff staff = (Staff) o;
        return Objects.equals(getAsurite(), staff.getAsurite());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAsurite());
    }
}
