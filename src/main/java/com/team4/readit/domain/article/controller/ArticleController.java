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
@RequestMapping("/api/v1")
public class ArticleController {
    private final ArticleService articleService;

    @GetMapping("/articles/job")
    public ResponseEntity<?> getPopularArticlesByJob(@RequestParam Long jobId) {
        return articleService.getPopularArticlesByJob(jobId);
    }
}
