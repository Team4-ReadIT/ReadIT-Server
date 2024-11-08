package com.team4.readit.domain.article.controller;

import com.team4.readit.domain.article.service.ArticleService;
import com.team4.readit.domain.article.service.KeywordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/keyword")
public class KeywordController {
    private final KeywordService keywordService;
    @GetMapping("/img")
    public ResponseEntity<?> getLatestKeywordImage(Authentication authentication) {
        return keywordService.getLatestKeywordImage();
    }
}
