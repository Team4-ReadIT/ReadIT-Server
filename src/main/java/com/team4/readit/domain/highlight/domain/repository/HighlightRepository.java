package com.team4.readit.domain.highlight.domain.repository;

import com.team4.readit.domain.highlight.domain.Highlight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HighlightRepository extends JpaRepository<Highlight, Long> {
}