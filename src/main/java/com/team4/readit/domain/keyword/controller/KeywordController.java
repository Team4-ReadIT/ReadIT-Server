package com.team4.readit.domain.keyword.controller;

import com.team4.readit.domain.keyword.service.KeywordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class KeywordController {
    private final KeywordService keywordService;
}
