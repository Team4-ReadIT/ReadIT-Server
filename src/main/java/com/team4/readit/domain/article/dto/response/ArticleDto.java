package com.team4.readit.domain.article.dto.response;

public record ArticleDto(
        Long id, String title, String pubDate, String source, String summary, String articleLink, int viewCount, String imgUrl, Boolean isScrapped
) {}