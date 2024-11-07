package com.team4.readit.domain.mindmap.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.Map;

public record SaveMindmapRequestDto(
        @NotNull(message = "recordId는 필수입니다.") Long articleId,
        @NotNull(message = "mindmap은 필수입니다.") Map<String, Object> mindmap) {}
