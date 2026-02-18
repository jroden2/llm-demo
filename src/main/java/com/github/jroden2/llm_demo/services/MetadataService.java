package com.github.jroden2.llm_demo.services;

import com.github.jroden2.llm_demo.models.EnrichedMetadata;
import io.github.jroden2.armco.ArmcoClient;
import io.github.jroden2.armco.model.ExtractionRequest;
import io.github.jroden2.armco.model.OutputFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

@Service("metadataService")
public class MetadataService {

    private static final Logger logger = LoggerFactory.getLogger(MetadataService.class);
    private final ArmcoClient armcoClient;
    private final ObjectMapper objectMapper;

    public MetadataService(ArmcoClient armcoClient, ObjectMapper objectMapper) {
        this.armcoClient = armcoClient;
        this.objectMapper = objectMapper;
    }

    private static final String SCHEMA = """
        {
          "title": string | null,
          "description": string | null,
          "keywords": string[],
          "human_context": string | null
        }
        """;

    public EnrichedMetadata enrich(String title, String content) {
        String combined = "Title: " + title + "\n\n" + content;

        ExtractionRequest request = ExtractionRequest.builder(combined)
                .fields("title", "description", "keywords", "human_context")
                .outputFormat(OutputFormat.JSON)
                .schema(SCHEMA)
                .build();

        try {
            String raw = armcoClient.extract(request);
            JsonNode node = objectMapper.readTree(raw);

            List<String> keywords = new ArrayList<>();
            node.path("keywords").forEach(k -> keywords.add(k.asText()));

            return EnrichedMetadata.builder()
                    .description(node.path("description").asText(null))
                    .keywords(keywords)
                    .humanContext(node.path("human_context").asText(null))
                    .build();

        } catch (Exception e) {
            logger.error("Enrichment failed for article with title: {}", title, e);
            return EnrichedMetadata.builder().build();
        }
    }

}
