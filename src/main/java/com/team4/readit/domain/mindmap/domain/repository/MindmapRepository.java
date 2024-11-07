package com.team4.readit.domain.mindmap.domain.repository;

import com.team4.readit.domain.mindmap.domain.Mindmap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MindmapRepository extends JpaRepository<Mindmap, Long> {
    List<Mindmap> findByUserIdAndArticleId(Long userId, Long articleId);
}