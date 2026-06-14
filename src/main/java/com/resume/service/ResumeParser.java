package com.resume.service;

import com.resume.model.ResumeData;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.*;
import java.util.*;
import java.util.regex.*;

@Service
public class ResumeParser {

    private static final Pattern EMAIL = Pattern.compile("[\\w.-]+@[\\w.-]+");
    private static final Pattern PHONE = Pattern.compile("1[3-9]\\d{9}");
    private static final Pattern SCHOOL = Pattern.compile("([\\u4e00-\\u9fa5]+大学|University of [\\w ]+)");
    private static final Pattern ENGLISH_CERT = Pattern.compile("(CET-[46]|英语[四六]级|IELTS|TOEFL)");

    private static final Set<String> TECH_SKILLS = new HashSet<>(Arrays.asList(
        "Java","Spring Boot","MyBatis","MySQL","Redis","C++","Python","Go","JavaScript",
        "Vue","React","Docker","Kubernetes","Linux","Git","Maven","Nginx",
        "微服务","多线程","Socket","RESTful","MongoDB","Elasticsearch"
    ));

    public ResumeData parse(MultipartFile file) throws IOException {
        String text;
        if (file.getOriginalFilename() != null && file.getOriginalFilename().endsWith(".pdf")) {
            text = parsePdf(file);
        } else {
            text = new String(file.getBytes());
        }
        return extract(text);
    }

    private String parsePdf(MultipartFile file) throws IOException {
        try (PDDocument doc = Loader.loadPDF(file.getBytes())) {
            PDFTextStripper stripper = new PDFTextStripper();
            stripper.setSortByPosition(true);
            return stripper.getText(doc);
        }
    }

    private ResumeData extract(String text) {
        ResumeData data = new ResumeData();
        data.setRawText(text);

        // Extract email
        Matcher m = EMAIL.matcher(text);
        if (m.find()) data.setEmail(m.group());

        // Extract phone
        m = PHONE.matcher(text);
        if (m.find()) data.setPhone(m.group());

        // Extract school
        m = SCHOOL.matcher(text);
        if (m.find()) data.setSchool(m.group());

        // Extract skills
        List<String> skills = new ArrayList<>();
        for (String skill : TECH_SKILLS) {
            if (text.toLowerCase().contains(skill.toLowerCase())) {
                skills.add(skill);
            }
        }
        data.setSkills(skills);

        // English cert
        data.setHasEnglishCert(ENGLISH_CERT.matcher(text).find());

        // Has internship keyword
        data.setHasInternship(
            text.contains("实习") && (text.contains("公司") || text.contains("科技") || text.contains("技术"))
        );

        // Detect projects
        List<String> projects = new ArrayList<>();
        String[] projMarkers = {"项目经历", "项目经验", "PROJECTS", "项目"};
        for (String marker : projMarkers) {
            int idx = text.indexOf(marker);
            if (idx >= 0) {
                String after = text.substring(idx);
                // Simple extraction: take lines after the marker
                String[] lines = after.split("\n");
                for (int i = 1; i < Math.min(lines.length, 10); i++) {
                    String line = lines[i].trim();
                    if (line.length() > 20 && !line.contains("教育") && !line.contains("技能")) {
                        projects.add(line.length() > 80 ? line.substring(0, 80) + "..." : line);
                    }
                }
                break;
            }
        }
        data.setProjects(projects);

        return data;
    }
}
