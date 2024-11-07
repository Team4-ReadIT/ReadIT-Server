package com.team4.readit.domain.highlight.controller;

import com.team4.readit.domain.highlight.dto.request.HighlightSaveRequestDto;
import com.team4.readit.domain.highlight.service.HighlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/highlight")
public class HighlightController {
    private final HighlightService highlightService;

    @PostMapping
    public ResponseEntity<?> saveHighlight(@RequestBody HighlightSaveRequestDto highlightSaveRequestDto, @RequestParam("userId") Long userId) {
        return highlightService.saveHighlight(highlightSaveRequestDto, userId);
    }
}
