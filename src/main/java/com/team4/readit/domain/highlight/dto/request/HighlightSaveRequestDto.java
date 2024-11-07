package com.team4.readit.domain.highlight.dto.request;

import com.team4.readit.domain.highlight.dto.HighlightDto;

import java.util.List;

public record HighlightSaveRequestDto(Long recordId, List<HighlightDto> highlights) {}
