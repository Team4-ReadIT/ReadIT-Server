package com.team4.readit.domain.mindmap.dto.request;

import java.util.Map;

public record SaveMindmapRequestDto(Long articleId, Map<String, Object> mindmap) {}
