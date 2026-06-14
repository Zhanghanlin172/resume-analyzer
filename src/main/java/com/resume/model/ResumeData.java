package com.resume.model;

import java.util.List;

public class ResumeData {
    private String name;
    private String email;
    private String phone;
    private String school;
    private String major;
    private List<String> skills;
    private List<String> projects;
    private boolean hasEnglishCert;
    private boolean hasInternship;
    private String rawText;

    // getters/setters
    public String getName() { return name; }
    public void setName(String n) { this.name = n; }
    public String getEmail() { return email; }
    public void setEmail(String e) { this.email = e; }
    public String getPhone() { return phone; }
    public void setPhone(String p) { this.phone = p; }
    public String getSchool() { return school; }
    public void setSchool(String s) { this.school = s; }
    public String getMajor() { return major; }
    public void setMajor(String m) { this.major = m; }
    public List<String> getSkills() { return skills; }
    public void setSkills(List<String> s) { this.skills = s; }
    public List<String> getProjects() { return projects; }
    public void setProjects(List<String> p) { this.projects = p; }
    public boolean isHasEnglishCert() { return hasEnglishCert; }
    public void setHasEnglishCert(boolean h) { this.hasEnglishCert = h; }
    public boolean isHasInternship() { return hasInternship; }
    public void setHasInternship(boolean h) { this.hasInternship = h; }
    public String getRawText() { return rawText; }
    public void setRawText(String r) { this.rawText = r; }
}
