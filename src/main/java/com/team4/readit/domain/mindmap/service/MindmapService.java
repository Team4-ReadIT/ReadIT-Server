package com.team4.readit.domain.mindmap.service;

import com.team4.readit.domain.mindmap.domain.Mindmap;
import com.team4.readit.domain.mindmap.domain.repository.MindmapRepository;
import com.team4.readit.domain.mindmap.dto.response.GetMindmapResponseDto;
import com.team4.readit.global.converter.MindmapDtoConverter;
import com.team4.readit.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MindmapService {
    private final MindmapRepository mindmapRepository;

    public ResponseEntity<?> buildMindmapHierarchy(Long userId, Long articleId) {
        List<Mindmap> mindmaps = mindmapRepository.findByUserIdAndArticleId(userId, articleId);

        // 마인드맵이 존재하면 계층 구조를 반환
        Map<String, Object> result = mindmaps.stream()
                .filter(mindmap -> mindmap.getParent() == null)  // 최상위 노드만 필터링
                .collect(Collectors.toMap(Mindmap::getContent, this::buildHierarchy));

        GetMindmapResponseDto getMindmapResponseDto = MindmapDtoConverter.convertToMindmapDto(result);
        return ResponseEntity.ok(ApiResponse.success(getMindmapResponseDto, "마인드맵 조회 성공"));
    }

    private Map<String, Object> buildHierarchy(Mindmap mindmap) {
        return mindmap.getChildren().stream()
                .collect(Collectors.toMap(Mindmap::getContent, this::buildHierarchy));  // 자식이 있으면 재귀 호출
    }
}
