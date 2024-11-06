package com.team4.readit.domain.article.domain.repository;

import com.team4.readit.domain.article.domain.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KeywordRepository extends JpaRepository<Keyword, Long> {
    Optional<Keyword> findTopByOrderByCreatedAtDesc();
}