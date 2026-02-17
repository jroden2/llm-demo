package com.github.jroden2.llm_demo.models;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class EnrichedMetadata {

    private String description;
    private List<String> keywords;
    private String humanContext;

    public EnrichedMetadata empty() {
        return EnrichedMetadata.builder()
                .keywords(List.of())
                .build();
    }

}
