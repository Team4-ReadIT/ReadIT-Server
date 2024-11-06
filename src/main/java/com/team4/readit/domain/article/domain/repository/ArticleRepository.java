package com.team4.readit.domain.article.domain.repository;

import com.team4.readit.domain.article.domain.Article;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Query("SELECT a FROM Article a WHERE a.pubDate >= :startDate " +
            "AND a.id IN (SELECT av.article.id FROM ArticleView av WHERE av.job.id = :jobId) " +
            "ORDER BY a.viewCount DESC")
    List<Article> findTop5PopularArticlesByJobId(@Param("jobId") Long jobId,
                                                 @Param("startDate") LocalDateTime startDate);

    @Query("SELECT a FROM Article a " +
            "WHERE a.pubDate >= :startDate " +
            "ORDER BY a.viewCount DESC, a.scrapCount DESC")
    List<Article> findTopArticlesByTimePeriod(@Param("startDate") LocalDateTime startDate, Pageable pageable);
}