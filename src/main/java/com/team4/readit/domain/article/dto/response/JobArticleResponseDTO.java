package com.team4.readit.domain.article.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JobArticleResponseDTO {
    private Long id;
    private String title;
    private String imgUrl;
    private String source;
}
