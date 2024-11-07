package com.team4.readit.domain.scrap.service;

import com.team4.readit.domain.article.domain.Article;
import com.team4.readit.domain.article.domain.repository.ArticleRepository;
import com.team4.readit.domain.article.service.ArticleService;
import com.team4.readit.domain.scrap.domain.Scrap;
import com.team4.readit.domain.scrap.domain.repository.ScrapRepository;
import com.team4.readit.domain.scrap.dto.response.ScrapedArticleResponse;
import com.team4.readit.domain.user_info.domain.UserInfo;
import com.team4.readit.domain.user_info.domain.repository.UserInfoRepository;
import com.team4.readit.domain.user_info.service.UserInfoUtil;
import com.team4.readit.global.exception.ExceptionCode;
import com.team4.readit.global.exception.InvalidInputException;
import com.team4.readit.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ScrapService {
    private final ScrapRepository scrapRepository;
    private final ArticleService articleService;
    private final UserInfoUtil userInfoUtil;

    @Transactional
    public ResponseEntity<?> toggleScrap(Long userId, Long articleId) {
        // TODO 로그인 토큰에서 이메일 추출하여 유저 정보 가져오기
        UserInfo userInfo = userInfoUtil.getUserInfoById(userId);

        Article article = articleService.getArticleById(articleId);

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

    public ResponseEntity<?> getMyScraps(Long userId) {
        // TODO 로그인 토큰에서 이메일 추출하여 유저 정보 가져오기
        UserInfo userInfo = userInfoUtil.getUserInfoById(userId);

        // 사용자 스크랩 목록을 가져오고, 필요한 데이터만 반환하는 DTO로 변환
        List<ScrapedArticleResponse> scraps = userInfo.getScraps().stream()
                .map(scrap -> new ScrapedArticleResponse(
                        scrap.getId(),
                        scrap.getArticle().getTitle(),
                        scrap.getArticle().getImgUrl(),
                        scrap.getArticle().getSource()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponse.success(scraps, "스크랩 목록 조회 성공"));
    }

    public boolean isArticleScappedByUser(Long userId, Long articleId) {
        return scrapRepository.existsByUserIdAndArticleId(userId, articleId);
    }
}
