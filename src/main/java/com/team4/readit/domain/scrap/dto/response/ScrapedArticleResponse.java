package com.team4.readit.domain.scrap.dto.response;

public record ScrapedArticleResponse(Long articleId, String title, String imageUrl, String source) {
}
