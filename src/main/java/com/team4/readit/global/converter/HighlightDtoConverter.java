package com.team4.readit.global.converter;

import com.team4.readit.domain.article.domain.Article;
import com.team4.readit.domain.highlight.domain.Highlight;
import com.team4.readit.domain.highlight.dto.HighlightDto;
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

    public static List<Highlight> toEntities(List<HighlightDto> highlightDtos, Long recordId, UserInfo user, Article article) {
        return highlightDtos.stream()
                .map(dto -> Highlight.builder()
                        .startIndex(dto.startIndex())
                        .endIndex(dto.endIndex())
                        .memoText(dto.memoText())
                        .user(user)
                        .article(article)
                        .build())
                .collect(Collectors.toList());
    }
}
