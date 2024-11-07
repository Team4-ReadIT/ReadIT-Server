package com.team4.readit.global.converter;

import com.team4.readit.domain.article.domain.Article;
import com.team4.readit.domain.article.dto.response.*;
import com.team4.readit.domain.highlight.dto.HighlightDto;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class ArticleDtoConverter {

    // Article -> ArticleListResponseDTO 변환 메서드
    public static ArticleListResponseDTO convertToArticleListResponseDTO(Article article) {
        return new ArticleListResponseDTO(
                article.getId(),
                article.getTitle(),
                article.getImgUrl(),
                article.getSource()
        );
    }

    // Article -> ArticleDto 변환 메서드
    public static ArticleDto convertToArticleDto(Article article, Boolean isScrapped) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        String formattedPubDate = article.getPubDate().format(formatter);

        return new ArticleDto(
                article.getId(),
                article.getTitle(),
                formattedPubDate,
                article.getSource(),
                article.getSummary(),
                article.getArticleLink(),
                article.getViewCount(),
                article.getImgUrl(),
                isScrapped
        );
    }

    // Article, Mindmap, Highlight -> ArticleDetailResponseDto 변환 메서드
    public static ArticleDetailResponseDto convertToArticleDetailResponseDto(ArticleDto articleDto, List<HighlightDto> highlightDto) {
        return new ArticleDetailResponseDto(articleDto, highlightDto);
    }
}
