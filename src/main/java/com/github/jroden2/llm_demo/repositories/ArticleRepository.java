package com.github.jroden2.llm_demo.repositories;

import com.github.jroden2.llm_demo.models.Article;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ArticleRepository extends MongoRepository<Article, String> {
}
