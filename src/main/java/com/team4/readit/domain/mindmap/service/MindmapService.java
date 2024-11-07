package com.team4.readit.domain.mindmap.service;

import com.team4.readit.domain.mindmap.domain.Mindmap;
import com.team4.readit.domain.mindmap.domain.repository.MindmapRepository;
import com.team4.readit.domain.mindmap.dto.response.MindmapDto;
import com.team4.readit.global.converter.MindmapDtoConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MindmapService {
    private final MindmapRepository mindmapRepository;

    public MindmapDto getMindmapHierarchy(Long userId, Long articleId) {
        List<Mindmap> mindmaps = mindmapRepository.findByUserIdAndArticleId(userId, articleId);

        // 마인드맵이 존재하면 계층 구조를 반환
        Map<String, Object> result = new HashMap<>();
        if (!mindmaps.isEmpty()) {
            for (Mindmap mindmap : mindmaps) {
                if (mindmap.getParent() == null) {
                    result.put(mindmap.getContent(), buildHierarchy(mindmap));  // 최상위 마인드맵만 처리
                }
            }
        }
        return MindmapDtoConverter.convertToMindmapDto(result);
    }

    // 마인드맵의 계층 구조를 재귀적으로 구축
    private Map<String, Object> buildHierarchy(Mindmap mindmap) {
        Map<String, Object> hierarchy = new HashMap<>();
        // 자식 마인드맵이 있으면, 재귀적으로 자식들을 처리
        for (Mindmap child : mindmap.getChildren()) {
            hierarchy.put(child.getContent(), buildHierarchy(child));  // 자식이 있으면 재귀적으로 호출
        }
        return hierarchy.isEmpty() ? new HashMap<>() : hierarchy;  // 자식이 없다면 빈 맵을 반환
    }
}
