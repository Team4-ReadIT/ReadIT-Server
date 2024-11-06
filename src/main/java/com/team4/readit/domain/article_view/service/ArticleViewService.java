package com.team4.readit.domain.article_view.service;

import com.team4.readit.domain.article.domain.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ArticleViewService {
    private final ArticleRepository articleRepository;
}
