package com.team4.readit.domain.article.controller;

import com.team4.readit.domain.article.service.ArticleService;
import com.team4.readit.domain.user_info.domain.UserInfo;
import com.team4.readit.domain.user_info.service.UserInfoUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/articles")
public class ArticleController {
    private final ArticleService articleService;
    @GetMapping("/job")
    public ResponseEntity<?> getTopArticlesByJob(Authentication authentication) {
        return articleService.getTopArticlesByJob(authentication.getName());
    }
    @GetMapping("/popular")
    public ResponseEntity<?> getTopArticles(Authentication authentication, @RequestParam String time) {
        return articleService.getTopArticles(time);
    }

    @GetMapping
    public ResponseEntity<?> getAllArticles(Authentication authentication) {
        return articleService.getAllArticles();
    }

    @GetMapping("/{articleId}")
    public ResponseEntity<?> getArticleDetail(Authentication authentication, @PathVariable Long articleId) {
        return articleService.getArticleDetail(authentication.getName(), articleId);
    }
}
