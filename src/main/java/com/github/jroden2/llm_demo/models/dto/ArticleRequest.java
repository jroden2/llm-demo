package com.github.jroden2.llm_demo.models.dto;

import lombok.Data;

@Data
public class ArticleRequest {
    private String title;
    private String content;
    private String authorId;
}
