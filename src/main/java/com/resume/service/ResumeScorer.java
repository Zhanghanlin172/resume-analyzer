package com.resume.service;

import com.resume.model.AnalysisResult;
import com.resume.model.ResumeData;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ResumeScorer {

    private static final Set<String> HOT_KEYWORDS = new HashSet<>(Arrays.asList(
        "Java","Spring","MyBatis","MySQL","Redis","微服务","Docker","Linux",
        "C++","Python","Go","JavaScript","Vue","React","Git","多线程",
        "设计模式","数据结构","算法","计算机网络","操作系统"
    ));

    public AnalysisResult analyze(ResumeData data) {
        AnalysisResult r = new AnalysisResult();

        // 1. Keyword score (40%)
        int kwCount = 0;
        for (String kw : HOT_KEYWORDS) {
            if (data.getRawText().toLowerCase().contains(kw.toLowerCase())) kwCount++;
        }
        r.setKeywordScore(Math.min(40, kwCount * 4));

        // 2. Structure score (25%)
        int structure = 0;
        if (data.getName() != null || data.getEmail() != null) structure += 6;
        if (data.getPhone() != null) structure += 4;
        if (data.getSchool() != null) structure += 5;
        if (data.getSkills() != null && data.getSkills().size() >= 5) structure += 6;
        if (data.isHasEnglishCert()) structure += 4;
        r.setStructureScore(structure);

        // 3. Experience score (20%)
        int exp = 0;
        if (data.getProjects() != null && !data.getProjects().isEmpty()) exp += 10;
        if (data.isHasInternship()) exp += 10;
        r.setExperienceScore(exp);

        // 4. ATS readability (15%)
        int ats = 10;
        String text = data.getRawText();
        if (text.contains("|") || text.contains("·")) ats += 3;
        if (text.length() > 300) ats += 2;
        r.setAtsScore(ats);

        // Total
        r.setTotalScore(r.getKeywordScore() + r.getStructureScore() + r.getExperienceScore() + r.getAtsScore());

        // Level
        if (r.getTotalScore() >= 75) r.setLevel("优秀");
        else if (r.getTotalScore() >= 55) r.setLevel("良好");
        else if (r.getTotalScore() >= 35) r.setLevel("一般");
        else r.setLevel("需改进");

        return r;
    }

    public void generateSuggestions(ResumeData data, AnalysisResult result) {
        List<String> strengths = new ArrayList<>();
        List<String> weaknesses = new ArrayList<>();
        List<String> suggestions = new ArrayList<>();
        List<String> missing = new ArrayList<>();

        if (data.getSkills() != null && data.getSkills().size() >= 6) {
            strengths.add("技术关键词丰富（" + data.getSkills().size() + "个）");
        } else {
            weaknesses.add("技术关键词偏少，建议补充到6个以上");
        }

        if (data.isHasEnglishCert()) {
            strengths.add("有英语证书（CET-4/6 或同等）");
        } else {
            weaknesses.add("缺少英语水平证明");
            suggestions.add("如有 CET-4/6 请在简历中注明");
        }

        if (data.isHasInternship()) {
            strengths.add("有实习经历关键词");
        } else {
            weaknesses.add("未检测到实习经历");
            suggestions.add("如有项目经历请重点突出，可弥补缺乏实习的短板");
        }

        if (data.getProjects() != null && !data.getProjects().isEmpty()) {
            strengths.add("包含项目经历描述");
        } else {
            weaknesses.add("缺少项目经历");
            suggestions.add("添加2-3个技术项目，注明使用的技术和解决的问题");
        }

        if (data.getSchool() != null && data.getSchool().contains("大学")) {
            strengths.add("教育信息完整");
        }

        // Missing keywords
        Set<String> found = new HashSet<>();
        for (String kw : HOT_KEYWORDS) {
            if (data.getRawText().toLowerCase().contains(kw.toLowerCase())) found.add(kw);
        }
        if (found.size() < 8) {
            List<String> candidates = new ArrayList<>(HOT_KEYWORDS);
            candidates.removeAll(found);
            Collections.shuffle(candidates);
            missing = candidates.subList(0, Math.min(5, candidates.size()));
        }

        suggestionTips(result.getTotalScore(), suggestions);

        result.setStrengths(strengths);
        result.setWeaknesses(weaknesses);
        result.setSuggestions(suggestions);
        result.setMissingKeywords(missing);
    }

    private void suggestionTips(int score, List<String> suggestions) {
        if (score < 40) {
            suggestions.add("简历整体需要大幅优化：增加技术关键词、完善项目描述、添加证书信息");
        } else if (score < 60) {
            suggestions.add("简历有一定基础，建议重点补充项目细节和量化成果");
        } else if (score < 80) {
            suggestions.add("简历质量不错，可在项目描述中加入具体数据（如QPS、用户量、代码行数）提升说服力");
        } else {
            suggestions.add("简历质量优秀，建议针对性准备面试项目问答和算法题");
        }
    }
}
