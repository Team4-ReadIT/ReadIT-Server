package com.team4.readit.domain.article.controller;

import com.team4.readit.domain.article.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/articles")
public class ArticleController {
    private final ArticleService articleService;

    @GetMapping("/job")
    public ResponseEntity<?> getTopArticlesByJob(@RequestParam Long userId) {
        return articleService.getTopArticlesByJob(userId);
    }
    @GetMapping("/popular")
    public ResponseEntity<?> getTopArticles(@RequestParam String time) {
        return articleService.getTopArticles(time);
    }

    @GetMapping
    public ResponseEntity<?> getAllArticles() {
        return articleService.getAllArticles();
    }

    @GetMapping("/{articleId}")
    public ResponseEntity<?> getArticleDetail(@PathVariable Long articleId, @RequestParam Long userId) {
        return articleService.getArticleDetail(articleId, userId);
    }
}
