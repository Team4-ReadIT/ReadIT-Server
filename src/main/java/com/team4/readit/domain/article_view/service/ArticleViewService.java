package com.team4.readit.domain.article_view.service;

import com.team4.readit.domain.article.domain.Article;
import com.team4.readit.domain.article_view.domain.ArticleView;
import com.team4.readit.domain.article_view.domain.repository.ArticleViewRepository;
import com.team4.readit.domain.job.domain.Job;
import com.team4.readit.domain.user_info.domain.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ArticleViewService {
    private final ArticleViewRepository articleViewRepository;

    @Transactional
    public void increaseViewCount(UserInfo userInfo, Job job, Article article) {
        // 사용자가 이미 조회한 기록이 없으면 새로 추가하고 조회수 증가
        articleViewRepository.findByUserIdAndJobIdAndArticleId(userInfo.getId(), job != null ? job.getId() : null, article.getId())
                .orElseGet(() -> {
                    ArticleView articleView = ArticleView.builder()
                            .user(userInfo)
                            .job(job)
                            .article(article)
                            .build();
                    articleViewRepository.save(articleView);
                    article.incrementViewCount();
                    return articleView;
                });
        log.info("조회수 증가X, 현재 조회수: {}", article.getViewCount());
    }
}
