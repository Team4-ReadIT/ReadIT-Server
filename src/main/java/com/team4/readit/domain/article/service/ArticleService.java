package com.team4.readit.domain.article.service;

import com.team4.readit.domain.article.domain.Article;
import com.team4.readit.domain.article.domain.Keyword;
import com.team4.readit.domain.article.domain.repository.ArticleRepository;
import com.team4.readit.domain.article.domain.repository.KeywordRepository;
import com.team4.readit.domain.article.dto.response.ArticleListResponseDTO;
import com.team4.readit.global.exception.ExceptionCode;
import com.team4.readit.global.exception.InvalidInputException;
import com.team4.readit.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final KeywordRepository keywordRepository;

    public ResponseEntity<?> getTopArticlesByJob(Long jobId) {
        LocalDateTime oneWeekAgo = LocalDateTime.now().minusWeeks(1)
                .withHour(0)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);

        log.info("oneWeekAgo: {}", oneWeekAgo);

        List<Article> articles = articleRepository.findTop5PopularArticlesByJobId(jobId, oneWeekAgo);

        // Article -> PopularArticleResponseDTO로 변환
        List<ArticleListResponseDTO> articleDTOs = articles.stream()
                .map(article -> new ArticleListResponseDTO(
                        article.getId(),
                        article.getTitle(),
                        article.getImgUrl(),
                        article.getSource()
                ))
                .toList();

        return ResponseEntity.ok(ApiResponse.success(articleDTOs, "사용자 직무의 인기 기사 조회 성공"));
    }

    public ResponseEntity<?> getLatestKeywordImage() {
        String latestKeywordImgUrl = keywordRepository.findTopByOrderByCreatedAtDesc()
                .map(Keyword::getImgUrl)
                .orElse("");
        log.info("Latest Keyword ImgUrl: {}", latestKeywordImgUrl);
        return ResponseEntity.ok(ApiResponse.success(latestKeywordImgUrl, "최신 키워드 이미지 URL 조회 성공"));
    }

    public ResponseEntity<?> getTopArticles(String time) {
        LocalDateTime startDate = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);  // 기본적으로 오늘 00:00
        switch (time) {
            case "day" -> {
            }
            // 오늘 00:00
            case "week" ->
                // 지난 1주일 동안
                    startDate = startDate.minusWeeks(1);  // 7일 전
            case "month" ->
                // 지난 1달 동안
                    startDate = startDate.minusMonths(1);  // 한 달 전
            default -> throw new InvalidInputException(ExceptionCode.INVALID_PERIOD);
        }

        log.info("조회 기간 시작일: {}", startDate);

        // 해당 기간에 맞는 인기 기사 조회 (조회수 및 스크랩 수 기준으로 상위 5개)
        Pageable pageable = PageRequest.of(0, 5, Sort.by(Sort.Order.desc("viewCount"), Sort.Order.desc("scrapCount")));
        List<Article> topArticles = articleRepository.findTopArticlesByTimePeriod(startDate, pageable);


        // Article -> PopularArticleResponseDTO로 변환
        List<ArticleListResponseDTO> articleDTOs = topArticles.stream()
                .map(article -> new ArticleListResponseDTO(
                        article.getId(),
                        article.getTitle(),
                        article.getImgUrl(),
                        article.getSource()
                ))
                .toList();

        log.info("인기 기사 조회 성공 (기간: {}, 기사 수: {})", time, topArticles.size());

        return ResponseEntity.ok(ApiResponse.success(articleDTOs, "인기 기사 조회 성공"));
    }

    public ResponseEntity<?> getAllArticles() {
        List<Article> articles = articleRepository.findAll();

        // Article -> PopularArticleResponseDTO로 변환
        List<ArticleListResponseDTO> articleDTOs = articles.stream()
                .map(article -> new ArticleListResponseDTO(
                        article.getId(),
                        article.getTitle(),
                        article.getImgUrl(),
                        article.getSource()
                ))
                .toList();

        return ResponseEntity.ok(ApiResponse.success(articleDTOs, "모든 기사 조회 성공"));
    }
}
