package com.team4.readit.domain.article.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SimilarDto {
    private Long id;
    private String title;
    private String imgUrl;
    private String source;
}