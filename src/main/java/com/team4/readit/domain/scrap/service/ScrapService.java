package com.team4.readit.domain.scrap.service;

import com.team4.readit.domain.article.domain.Article;
import com.team4.readit.domain.article.domain.repository.ArticleRepository;
import com.team4.readit.domain.scrap.domain.Scrap;
import com.team4.readit.domain.scrap.domain.repository.ScrapRepository;
import com.team4.readit.domain.user_info.domain.UserInfo;
import com.team4.readit.domain.user_info.domain.repository.UserInfoRepository;
import com.team4.readit.global.exception.ExceptionCode;
import com.team4.readit.global.exception.InvalidInputException;
import com.team4.readit.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ScrapService {
    private final ScrapRepository scrapRepository;
    private final UserInfoRepository userInfoRepository;
    private final ArticleRepository articleRepository;

    @Transactional
    public ResponseEntity<?> toggleScrap(Long userId, Long articleId) {
        UserInfo userInfo = userInfoRepository.findById(userId)
                .orElseThrow(() -> new InvalidInputException(ExceptionCode.INVALID_USER));
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new InvalidInputException(ExceptionCode.INVALID_ARTICLE));

        Scrap existingScrap = scrapRepository.findByArticleIdAndUserId(articleId, userId)
                .orElse(null); // Optional을 null로 대체

        if (existingScrap == null) {
            // 스크랩이 없으면 새로 생성
            Scrap newScrap = Scrap.builder()
                    .user(userInfo)
                    .article(article)
                    .scraped(true)
                    .build();

            scrapRepository.save(newScrap);
            article.incrementScrapCount();

            return ResponseEntity.ok(ApiResponse.success(true, "스크랩이 추가되었습니다."));
        } else {
            // 이미 스크랩이 존재하면 상태 토글
            existingScrap.toggleScraped();
            if (existingScrap.isScraped()) {
                article.incrementScrapCount();
                return ResponseEntity.ok(ApiResponse.success(true, "스크랩이 추가되었습니다."));
            } else {
                article.decrementScrapCount();
                return ResponseEntity.ok(ApiResponse.success(false, "스크랩이 취소되었습니다."));
            }
        }
    }
}
