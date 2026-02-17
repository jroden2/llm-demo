package com.github.jroden2.llm_demo.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@Document(collection = "articles")
public class Article {

    @Id
    private String id;

    private String title;
    private String content;
    private String authorId;

    private String enrichedDescription;
    private List<String> enrichedKeywords;
    private String enrichedHumanContext;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;
}
