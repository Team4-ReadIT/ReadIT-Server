package com.team4.readit.domain.mindmap.controller;

import com.team4.readit.domain.mindmap.service.MindmapService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class MindmapController {
    private final MindmapService mindmapService;
}
