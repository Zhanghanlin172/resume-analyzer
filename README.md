# 智能简历分析器

[![Java](https://img.shields.io/badge/Java-17-orange)](https://openjdk.org)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.2-brightgreen)](https://spring.io)

上传简历 PDF → 自动提取信息 → 四维打分 → 生成改进建议。

## 功能

- 📄 支持 PDF 和 TXT 格式上传
- 🔍 自动提取：姓名、邮箱、手机、学校、技能关键词、项目经历
- 📊 四维评分：关键词密度（40%）+ 结构完整（25%）+ 经历质量（20%）+ ATS 可读性（15%）
- 💡 智能建议：根据评分自动生成优缺点分析和改进方向
- 🌐 内置 Web 管理界面

## 快速开始

```bash
git clone https://github.com/Zhanghanlin172/resume-analyzer.git
cd resume-analyzer

# 需要 Java 17 + Maven
mvn spring-boot:run

# 打开 http://localhost:8080
```

## API

```bash
# 上传简历分析
curl -X POST http://localhost:8080/api/analyze \
  -F "file=@你的简历.pdf"
```

返回 JSON：简历提取信息 + 百分制评分 + 优缺点 + 改进建议。

## 技术栈

| 层 | 技术 |
|---|------|
| 后端框架 | Spring Boot 3.2 |
| PDF 解析 | Apache PDFBox 3.0 |
| 评分引擎 | 自实现规则引擎（四维加权） |
| 前端 | 原生 HTML/CSS/JS |
| 构建 | Maven |

## 项目结构

```
src/main/java/com/resume/
├── ResumeAnalyzerApplication.java   # 启动入口
├── controller/
│   └── AnalyzeController.java        # REST API
├── service/
│   ├── ResumeParser.java             # PDF 解析 + 信息提取
│   └── ResumeScorer.java             # 评分引擎 + 建议生成
├── model/
│   ├── ResumeData.java               # 简历数据模型
│   └── AnalysisResult.java           # 分析结果模型
└── resources/
    └── static/index.html             # Web 界面
```
