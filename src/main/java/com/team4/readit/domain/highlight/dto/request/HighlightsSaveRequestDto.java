package com.team4.readit.domain.highlight.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record HighlightsSaveRequestDto(
        @NotNull(message = "recordId는 필수입니다.") Long recordId,
        @NotNull(message = "highlights는 필수입니다.") @Valid List<HighlightSaveRequestDto> highlights
) {}