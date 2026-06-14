package com.resume.controller;

import com.resume.model.AnalysisResult;
import com.resume.model.ResumeData;
import com.resume.service.ResumeParser;
import com.resume.service.ResumeScorer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RestController
public class AnalyzeController {

    @Autowired
    private ResumeParser parser;

    @Autowired
    private ResumeScorer scorer;

    @PostMapping("/api/analyze")
    public ResponseEntity<Map<String, Object>> analyze(@RequestParam("file") MultipartFile file) {
        Map<String, Object> response = new HashMap<>();
        try {
            ResumeData data = parser.parse(file);
            AnalysisResult result = scorer.analyze(data);
            scorer.generateSuggestions(data, result);

            response.put("success", true);
            response.put("resume", data);
            response.put("result", result);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "解析失败：" + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
