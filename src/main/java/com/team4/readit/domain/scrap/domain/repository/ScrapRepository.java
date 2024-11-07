package com.team4.readit.domain.scrap.domain.repository;

import com.team4.readit.domain.scrap.domain.Scrap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ScrapRepository extends JpaRepository<Scrap, Long> {
    Optional<Scrap> findByArticleIdAndUserId(Long articleId, Long userId);

    boolean existsByUserIdAndArticleId(Long userId, Long articleId);
}