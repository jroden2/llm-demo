package com.github.jroden2.llm_demo.controllers;

import com.github.jroden2.llm_demo.models.dto.ArticleRequest;
import com.github.jroden2.llm_demo.models.dto.ArticleResponse;
import com.github.jroden2.llm_demo.services.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
public class ArticleController {

    private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping("/api/articles")
    public ResponseEntity<ArticleResponse> create(@RequestBody ArticleRequest request) {
        logger.info("Received article request: {}", request);
        return ResponseEntity.ok(articleService.save(request));
    }
}
