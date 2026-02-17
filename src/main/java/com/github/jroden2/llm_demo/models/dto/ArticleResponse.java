package com.github.jroden2.llm_demo.models.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@Builder
public class ArticleResponse {
    private String id;
    private String title;
    private String content;
    private String authorId;
    private String enrichedDescription;
    private List<String> enrichedKeywords;
    private String enrichedHumanContext;
    private Instant createdAt;
}
