package com.team4.readit.domain.article.service;

import com.team4.readit.domain.article.domain.Article;
import com.team4.readit.domain.article.domain.repository.ArticleRepository;
import com.team4.readit.global.exception.ExceptionCode;
import com.team4.readit.global.exception.InvalidInputException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ArticleHelperService {
    private final ArticleRepository articleRepository;
    public Article getArticleById(Long articleId) {
        return articleRepository.findById(articleId)
                .orElseThrow(() -> new InvalidInputException(ExceptionCode.INVALID_ARTICLE));
    }
}
