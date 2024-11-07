package com.team4.readit.global.converter;

import com.team4.readit.domain.highlight.domain.Highlight;
import com.team4.readit.domain.highlight.dto.response.HighlightDto;

public class HighlightDtoConverter {

    // Highlight -> HighlightDto로 변환
    public static HighlightDto convertToHighlightDto(Highlight highlight) {
        return new HighlightDto(
                highlight.getStartIndex(),
                highlight.getEndIndex(),
                highlight.getMemoText()
        );
    }
}
