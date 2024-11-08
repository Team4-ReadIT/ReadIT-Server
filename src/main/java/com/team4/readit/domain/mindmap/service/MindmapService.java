package com.team4.readit.domain.mindmap.service;

import com.team4.readit.domain.article.domain.Article;
import com.team4.readit.domain.article.service.ArticleHelperService;
import com.team4.readit.domain.mindmap.domain.Mindmap;
import com.team4.readit.domain.mindmap.domain.repository.MindmapRepository;
import com.team4.readit.domain.mindmap.dto.request.SaveMindmapRequestDto;
import com.team4.readit.domain.mindmap.dto.response.MindmapResponseDto;
import com.team4.readit.domain.user_info.domain.UserInfo;
import com.team4.readit.domain.user_info.service.UserInfoUtil;
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
    private final UserInfoUtil userInfoUtil;
    private final ArticleHelperService articleHelperService;

    public ResponseEntity<?> buildMindmapHierarchy(String email, Long articleId) {
        UserInfo userInfo = userInfoUtil.getUserInfoByEmail(email);
        Long userId = userInfo.getId();
        List<Mindmap> mindmaps = mindmapRepository.findByUserIdAndArticleId(userId, articleId);

        // 마인드맵이 존재하면 계층 구조를 반환
        Map<String, Object> result = mindmaps.stream()
                .filter(mindmap -> mindmap.getParent() == null)  // 최상위 노드만 필터링
                .collect(Collectors.toMap(Mindmap::getContent, this::buildHierarchy));

        MindmapResponseDto mindmapResponseDto = MindmapDtoConverter.convertToMindmapDto(result);
        return ResponseEntity.ok(ApiResponse.success(mindmapResponseDto, "마인드맵 조회 성공"));
    }

    private Map<String, Object> buildHierarchy(Mindmap mindmap) {
        return mindmap.getChildren().stream()
                .collect(Collectors.toMap(Mindmap::getContent, this::buildHierarchy));  // 자식이 있으면 재귀 호출
    }

    @Transactional
    public ResponseEntity<?> saveMindmap(String email, SaveMindmapRequestDto requestDto) {
        UserInfo user = userInfoUtil.getUserInfoByEmail(email);
        Article article = articleHelperService.getArticleById(requestDto.articleId());

        // 최상위 노드들에 대해 계층 구조 저장 시작
        saveMindmap(requestDto.mindmap(), null, user, article);

        return ResponseEntity.ok(ApiResponse.success(null, "마인드맵 저장 성공"));
    }

    private void saveMindmap(Map<String, Object> hierarchy, Mindmap parent, UserInfo user, Article article) {
        if (hierarchy == null || hierarchy.isEmpty()) return;

        for (Map.Entry<String, Object> entry : hierarchy.entrySet()) {
            // 현재 노드 저장
            Mindmap mindmap = createMindmap(entry.getKey(), parent, user, article);
            mindmapRepository.save(mindmap);

            // 자식 노드 처리
            if (entry.getValue() instanceof Map<?, ?>) {
                saveMindmap((Map<String, Object>) entry.getValue(), mindmap, user, article);
            }
        }
    }

    private Mindmap createMindmap(String content, Mindmap parent, UserInfo user, Article article) {
        return Mindmap.builder()
                .content(content)
                .user(user)
                .article(article)
                .parent(parent)
                .build();
    }
}
