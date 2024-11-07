package com.team4.readit.domain.article.service;

import com.team4.readit.domain.article.domain.Keyword;
import com.team4.readit.domain.article.domain.repository.KeywordRepository;
import com.team4.readit.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class KeywordService {
    private final KeywordRepository keywordRepository;

    public ResponseEntity<?> getLatestKeywordImage() {

        String latestKeywordImgUrl = keywordRepository.findTopByOrderByCreatedAtDesc()
                .map(Keyword::getImgUrl)
                .orElse("");
        log.info("Latest Keyword ImgUrl: {}", latestKeywordImgUrl);
        return ResponseEntity.ok(ApiResponse.success(latestKeywordImgUrl, "최신 키워드 이미지 URL 조회 성공"));
    }
}
