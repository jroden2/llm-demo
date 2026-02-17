package com.github.jroden2.llm_demo.services;

import com.github.jroden2.llm_demo.models.Article;
import com.github.jroden2.llm_demo.models.EnrichedMetadata;
import com.github.jroden2.llm_demo.models.dto.ArticleRequest;
import com.github.jroden2.llm_demo.models.dto.ArticleResponse;
import com.github.jroden2.llm_demo.repositories.ArticleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("articleService")
public class ArticleService {

    private static final Logger logger = LoggerFactory.getLogger(ArticleService.class);
    private final ArticleRepository articleRepository;
    private final MetadataService metadataService;

    public ArticleService(ArticleRepository articleRepository, MetadataService metadataService) {
        this.articleRepository = articleRepository;
        this.metadataService = metadataService;
    }

    public ArticleResponse save(ArticleRequest request) {
        logger.info("Saving article: {}", request);
        EnrichedMetadata metadata = metadataService
                .enrich(request.getTitle(), request.getContent());

        logger.info("Metadata enrichment complete: {}", metadata);

        Article article = Article.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .authorId(request.getAuthorId())
                .enrichedDescription(metadata.getDescription())
                .enrichedKeywords(metadata.getKeywords())
                .enrichedHumanContext(metadata.getHumanContext())
                .build();

        Article saved = articleRepository.save(article);
        return toResponse(saved);
    }

    private ArticleResponse toResponse(Article article) {
        logger.info("Converting article to response: {}", article);
        var resp = ArticleResponse.builder()
                .id(article.getId())
                .title(article.getTitle())
                .content(article.getContent())
                .authorId(article.getAuthorId())
                .enrichedDescription(article.getEnrichedDescription())
                .enrichedKeywords(article.getEnrichedKeywords())
                .enrichedHumanContext(article.getEnrichedHumanContext())
                .createdAt(article.getCreatedAt())
                .build();

        logger.info("Article response: {}", resp);
        return resp;
    }

}
