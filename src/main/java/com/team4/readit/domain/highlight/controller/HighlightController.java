package com.team4.readit.domain.highlight.controller;

import com.team4.readit.domain.highlight.service.HighlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class HighlightController {
    private final HighlightService highlightService;
}
