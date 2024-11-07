package com.team4.readit.domain.article.dto.response;

import com.team4.readit.domain.highlight.dto.response.HighlightDto;
import com.team4.readit.domain.mindmap.dto.response.MindmapDto;

public record ArticleDetailResponseDto(ArticleDto article, MindmapDto mindmap, HighlightDto highlight) {}