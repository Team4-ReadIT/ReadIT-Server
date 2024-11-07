package com.team4.readit.domain.mindmap.controller;

import com.team4.readit.domain.mindmap.dto.request.SaveMindmapRequestDto;
import com.team4.readit.domain.mindmap.service.MindmapService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/mindmap")
public class MindmapController {
    private final MindmapService mindmapService;

    @GetMapping("/{articleId}")
    private ResponseEntity<?> getMindmap(@RequestParam Long userId, @PathVariable Long articleId) {
        return mindmapService.buildMindmapHierarchy(userId, articleId);
    }
    @PostMapping
    public ResponseEntity<?> saveMindmap(@RequestBody @Valid SaveMindmapRequestDto requestDto, @RequestParam Long userId) {
        return mindmapService.saveMindmap(requestDto, userId);
    }
}
