package com.team4.readit.domain.article.controller;

import com.team4.readit.domain.article.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/articles")
public class ArticleController {
    private final ArticleService articleService;

    @GetMapping("/job")
    public ResponseEntity<?> getTopArticlesByJob(@RequestParam Long jobId) {
        return articleService.getTopArticlesByJob(jobId);
    }
    @GetMapping("/keyword-img")
    public ResponseEntity<?> getLatestKeywordImage() {
        return articleService.getLatestKeywordImage();
    }
    @GetMapping("/popular")
    public ResponseEntity<?> getTopArticles(@RequestParam String time) {
        return articleService.getTopArticles(time);
    }
}
