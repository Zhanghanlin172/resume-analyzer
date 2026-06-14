package com.resume.model;

import java.util.List;

public class AnalysisResult {
    private int totalScore;           // 0-100
    private int keywordScore;         // 关键词匹配度
    private int structureScore;       // 结构完整度
    private int experienceScore;      // 项目/实习质量
    private int atsScore;             // ATS 可读性

    private List<String> strengths;
    private List<String> weaknesses;
    private List<String> suggestions;
    private List<String> missingKeywords;

    private String level;  // 优秀 / 良好 / 一般 / 需改进

    // getters/setters
    public int getTotalScore() { return totalScore; }
    public void setTotalScore(int t) { this.totalScore = t; }
    public int getKeywordScore() { return keywordScore; }
    public void setKeywordScore(int k) { this.keywordScore = k; }
    public int getStructureScore() { return structureScore; }
    public void setStructureScore(int s) { this.structureScore = s; }
    public int getExperienceScore() { return experienceScore; }
    public void setExperienceScore(int e) { this.experienceScore = e; }
    public int getAtsScore() { return atsScore; }
    public void setAtsScore(int a) { this.atsScore = a; }
    public List<String> getStrengths() { return strengths; }
    public void setStrengths(List<String> s) { this.strengths = s; }
    public List<String> getWeaknesses() { return weaknesses; }
    public void setWeaknesses(List<String> w) { this.weaknesses = w; }
    public List<String> getSuggestions() { return suggestions; }
    public void setSuggestions(List<String> s) { this.suggestions = s; }
    public List<String> getMissingKeywords() { return missingKeywords; }
    public void setMissingKeywords(List<String> m) { this.missingKeywords = m; }
    public String getLevel() { return level; }
    public void setLevel(String l) { this.level = l; }
}
