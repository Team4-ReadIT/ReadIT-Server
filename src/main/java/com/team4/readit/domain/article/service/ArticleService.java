package com.team4.readit.domain.article.service;

import com.team4.readit.domain.article.domain.Article;
import com.team4.readit.domain.article.domain.Keyword;
import com.team4.readit.domain.article.domain.repository.ArticleRepository;
import com.team4.readit.domain.article.domain.repository.KeywordRepository;
import com.team4.readit.domain.highlight.dto.response.HighlightDto;
import com.team4.readit.global.converter.ArticleDtoConverter;
import com.team4.readit.domain.article.dto.response.*;
import com.team4.readit.domain.article_view.domain.repository.ArticleViewRepository;
import com.team4.readit.domain.article_view.service.ArticleViewService;
import com.team4.readit.domain.job.domain.Job;
import com.team4.readit.domain.job.domain.repository.JobRepository;
import com.team4.readit.domain.mindmap.service.MindmapService;
import com.team4.readit.domain.user_info.domain.UserInfo;
import com.team4.readit.domain.user_info.service.UserInfoUtil;
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
import java.util.Map;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final KeywordRepository keywordRepository;
    private final ArticleViewRepository articleViewRepository;
    private final JobRepository jobRepository;
    private final UserInfoUtil userInfoUtil;
    private final MindmapService mindmapService;
    private final ArticleViewService articleViewService;

    public ResponseEntity<?> getTopArticlesByJob(Long userId) {
        // TODO 로그인 토큰에서 이메일 추출하여 유저 정보 가져오기
        UserInfo userInfo = userInfoUtil.getUserInfoById(userId);

        if (userInfo.getJob() == null) {
            return ResponseEntity.ok(ApiResponse.success(null, "직무 정보가 없습니다."));
        }

        Long jobId = userInfo.getJob().getId();

        LocalDateTime oneWeekAgo = LocalDateTime.now().minusWeeks(1)
                .withHour(0)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);

        log.info("oneWeekAgo: {}", oneWeekAgo);

        List<Article> articles = articleRepository.findTop5PopularArticlesByJobId(jobId, oneWeekAgo);

        List<ArticleListResponseDTO> articleDTOs = articles.stream()
                .map(ArticleDtoConverter::convertToArticleListResponseDTO)
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

        List<ArticleListResponseDTO> articleDTOs = topArticles.stream()
                .map(ArticleDtoConverter::convertToArticleListResponseDTO)
                .toList();

        log.info("인기 기사 조회 성공 (기간: {}, 기사 수: {})", time, topArticles.size());

        return ResponseEntity.ok(ApiResponse.success(articleDTOs, "인기 기사 조회 성공"));
    }

    public ResponseEntity<?> getAllArticles() {
        List<Article> articles = articleRepository.findAll();

        List<ArticleListResponseDTO> articleDTOs = articles.stream()
                .map(ArticleDtoConverter::convertToArticleListResponseDTO)
                .toList();

        return ResponseEntity.ok(ApiResponse.success(articleDTOs, "모든 기사 조회 성공"));
    }

    @Transactional
    public ResponseEntity<?> getArticleById(Long articleId, Long userId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new InvalidInputException(ExceptionCode.INVALID_ARTICLE));

        // TODO 로그인 토큰에서 이메일 추출하여 유저 정보 가져오기
        UserInfo userInfo = userInfoUtil.getUserInfoById(userId);
        Job job = userInfo.getJob();

        // 사용자가 현재 직무에서 이전에 조회한 적이 없다면 조회수 증가
        // 사용자가 직무를 변경할 수 있으므로 조회수를 조회하는 기준에 job_id도 포함시켜야 함
        articleViewService.increaseViewCount(userInfo, job, article);

        // 마인드맵 계층 구조 조회
        Map<String, Object> mindmapHierarchy = mindmapService.getMindmapHierarchy(userId, articleId);

        // TODO 5. 형광펜 및 메모 내역 반환
        HighlightDto highlightDto = new HighlightDto("highlight");

        ArticleDetailResponseDto responseDto = ArticleDtoConverter.convertToArticleDetailResponseDto(
                article,
                mindmapHierarchy,
                highlightDto
        );

        return ResponseEntity.ok(ApiResponse.success(responseDto, "기사 상세 조회 성공"));
    }
}
