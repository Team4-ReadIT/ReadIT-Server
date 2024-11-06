package com.team4.readit.domain.mindmap.domain.repository;

import com.team4.readit.domain.mindmap.domain.Mindmap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MindmapRepository extends JpaRepository<Mindmap, Long> {
}