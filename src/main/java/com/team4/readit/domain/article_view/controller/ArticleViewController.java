package com.team4.readit.domain.article_view.controller;


import com.team4.readit.domain.article_view.service.ArticleViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class ArticleViewController {
    private final ArticleViewService articleViewService;
}
