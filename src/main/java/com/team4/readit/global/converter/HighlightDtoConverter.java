package com.team4.readit.global.converter;

import com.team4.readit.domain.article.domain.Article;
import com.team4.readit.domain.highlight.domain.Highlight;
import com.team4.readit.domain.highlight.dto.HighlightDto;
import com.team4.readit.domain.highlight.dto.request.HighlightSaveRequestDto;
import com.team4.readit.domain.user_info.domain.UserInfo;

import java.util.List;
import java.util.stream.Collectors;

public class HighlightDtoConverter {

    // Highlight -> HighlightDto로 변환
    public static HighlightDto convertToHighlightDto(Highlight highlight) {
        return new HighlightDto(
                highlight.getStartIndex(),
                highlight.getEndIndex(),
                highlight.getMemoText()
        );
    }

    public static List<Highlight> toEntities(List<HighlightSaveRequestDto> highlightDtos, UserInfo user, Article article) {
        return highlightDtos.stream()
                .map(dto -> Highlight.builder()
                        .startIndex(dto.startIndex() != null ? dto.startIndex() : 0)  // Integer -> int 변환, null일 경우 0으로 설정
                        .endIndex(dto.endIndex() != null ? dto.endIndex() : 0)
                        .memoText(dto.memoText())
                        .user(user)
                        .article(article)
                        .build())
                .collect(Collectors.toList());
    }
}
