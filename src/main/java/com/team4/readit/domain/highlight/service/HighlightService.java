package com.team4.readit.domain.highlight.service;

import com.team4.readit.domain.article.domain.Article;
import com.team4.readit.domain.article.service.ArticleHelperService;
import com.team4.readit.domain.highlight.domain.Highlight;
import com.team4.readit.domain.highlight.domain.repository.HighlightRepository;
import com.team4.readit.domain.highlight.dto.HighlightDto;
import com.team4.readit.domain.highlight.dto.request.HighlightsSaveRequestDto;
import com.team4.readit.domain.user_info.domain.UserInfo;
import com.team4.readit.domain.user_info.service.UserInfoUtil;
import com.team4.readit.global.converter.HighlightDtoConverter;
import com.team4.readit.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HighlightService {
    private final HighlightRepository highlightRepository;
    private final UserInfoUtil userInfoUtil;
    private final ArticleHelperService articleHelperService;

    public List<HighlightDto> getHighlightsByArticleAndUser(Long articleId, Long userId) {

        List<Highlight> highlights = highlightRepository.findAllByArticleIdAndUserId(articleId, userId);
        return highlights.stream()
                .map(HighlightDtoConverter::convertToHighlightDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public ResponseEntity<?> saveHighlight(HighlightsSaveRequestDto requestDto, Long userId) {
        UserInfo user = userInfoUtil.getUserInfoById(userId);
        Article article = articleHelperService.getArticleById(requestDto.recordId());

        // 하이라이트 저장 (변환 로직을 Converter에 위임)
        List<Highlight> highlights = HighlightDtoConverter.toEntities(requestDto.highlights(), user, article);
        highlightRepository.saveAll(highlights);

        return ResponseEntity.ok(ApiResponse.success(null, "하이라이트 저장 성공"));
    }
}
