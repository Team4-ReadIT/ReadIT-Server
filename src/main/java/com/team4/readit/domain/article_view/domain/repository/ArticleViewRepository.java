package com.team4.readit.domain.article_view.domain.repository;

import com.team4.readit.domain.article_view.domain.ArticleView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArticleViewRepository extends JpaRepository<ArticleView, Long> {
    Optional<ArticleView> findByUserIdAndJobIdAndArticleId(Long userId, Long jobId, Long articleId);
}