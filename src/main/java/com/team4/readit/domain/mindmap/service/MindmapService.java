package com.team4.readit.domain.mindmap.service;

import com.team4.readit.domain.article.domain.Article;
import com.team4.readit.domain.article.service.ArticleService;
import com.team4.readit.domain.mindmap.domain.Mindmap;
import com.team4.readit.domain.mindmap.domain.repository.MindmapRepository;
import com.team4.readit.domain.mindmap.dto.request.SaveMindmapRequestDto;
import com.team4.readit.domain.mindmap.dto.response.MindmapDto;
import com.team4.readit.domain.user_info.domain.UserInfo;
import com.team4.readit.domain.user_info.service.UserInfoUtil;
import com.team4.readit.global.converter.MindmapDtoConverter;
import com.team4.readit.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MindmapService {
    private final MindmapRepository mindmapRepository;
    private final UserInfoUtil userInfoUtil;
    private final ArticleService articleService;

    public MindmapDto buildMindmapHierarchy(Long userId, Long articleId) {
        List<Mindmap> mindmaps = mindmapRepository.findByUserIdAndArticleId(userId, articleId);

        // 마인드맵이 존재하면 계층 구조를 반환
        Map<String, Object> result = mindmaps.stream()
                .filter(mindmap -> mindmap.getParent() == null)  // 최상위 노드만 필터링
                .collect(Collectors.toMap(Mindmap::getContent, this::buildHierarchy));

        return MindmapDtoConverter.convertToMindmapDto(result);
    }

    private Map<String, Object> buildHierarchy(Mindmap mindmap) {
        return mindmap.getChildren().stream()
                .collect(Collectors.toMap(Mindmap::getContent, this::buildHierarchy));  // 자식이 있으면 재귀 호출
    }
}
