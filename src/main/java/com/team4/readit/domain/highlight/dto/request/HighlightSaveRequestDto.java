package com.team4.readit.domain.highlight.dto.request;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record HighlightSaveRequestDto(
        @NotNull(message = "startIndex는 필수입니다.")
        @Min(value = 0, message = "startIndex는 0 이상이어야 합니다.")
        Integer startIndex,

        @NotNull(message = "endIndex는 필수입니다.")
        @Min(value = 0, message = "endIndex는 0 이상이어야 합니다.")
        Integer endIndex,

        String memoText
) {

    @AssertTrue(message = "endIndex는 startIndex보다 작거나 같아야 합니다.")
    public boolean isEndIndexValid() {
        return startIndex == null || endIndex == null || startIndex <= endIndex;
    }
}