package com.team4.readit.domain.article.dto.response;

import com.team4.readit.domain.highlight.dto.response.HighlightDto;

import java.util.List;

public record ArticleDetailResponseDto(ArticleDto article, List<HighlightDto> highlight) {}