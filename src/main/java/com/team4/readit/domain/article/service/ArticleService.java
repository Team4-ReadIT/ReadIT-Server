package com.team4.readit.domain.article.service;

import com.team4.readit.domain.article.domain.Article;
import com.team4.readit.domain.article.domain.Keyword;
import com.team4.readit.domain.article.domain.repository.ArticleRepository;
import com.team4.readit.domain.article.domain.repository.KeywordRepository;
import com.team4.readit.domain.article.dto.response.*;
import com.team4.readit.domain.article_view.domain.ArticleView;
import com.team4.readit.domain.article_view.domain.repository.ArticleViewRepository;
import com.team4.readit.domain.highlight.domain.Highlight;
import com.team4.readit.domain.highlight.domain.repository.HighlightRepository;
import com.team4.readit.domain.job.domain.Job;
import com.team4.readit.domain.job.domain.repository.JobRepository;
import com.team4.readit.domain.mindmap.domain.Mindmap;
import com.team4.readit.domain.mindmap.domain.repository.MindmapRepository;
import com.team4.readit.domain.mindmap.service.MindmapService;
import com.team4.readit.domain.user_info.domain.UserInfo;
import com.team4.readit.domain.user_info.domain.repository.UserInfoRepository;
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
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final KeywordRepository keywordRepository;
    private final ArticleViewRepository articleViewRepository;
    private final JobRepository jobRepository;
    private final UserInfoRepository userInfoRepository;
    private final MindmapService mindmapService;

    public ResponseEntity<?> getTopArticlesByJob(Long jobId) {
        // TODO 로그인 토큰 -> 유저 -> 직무 정보 가져오기 (현재는 jobId로 받음)
        // TODO 직무 정보 없는 경우에는 data에 null 넣은 뒤 성공 리턴(200) or 실패 리턴(400) or 다른 방법으로 처리
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

    @Transactional
    public ResponseEntity<?> getArticleById(Long articleId, Long userId) {
        // 1. 기사 조회
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new InvalidInputException(ExceptionCode.INVALID_ARTICLE));

        // 2. 조회수 증가 (ArticleView 조회 및 없으면 생성)
        // TODO 로그인 토큰 -> 유저 추출 로 변경
        UserInfo userInfo = userInfoRepository.findById(userId)
                .orElseThrow(() -> new InvalidInputException(ExceptionCode.INVALID_USER));

        Long job_id = userInfo.getJob().getId();
        Job job = jobRepository.findById(job_id)
                .orElseThrow(() -> new InvalidInputException(ExceptionCode.INVALID_JOB));

        // 사용자가 도중에 직무를 변경할 수 있으므로 조회 기준에 job_id도 포함
        articleViewRepository.findByUserIdAndJobIdAndArticleId(userId, job_id, articleId)
                .orElseGet(() -> {
                    ArticleView articleView = ArticleView.builder()
                            .user(userInfo)
                            .job(job)
                            .article(article)
                            .build();
                    articleViewRepository.save(articleView);
                    article.incrementViewCount();
                    log.info("새로 생성된 조회수 엔티티 번호: {}, 현재 조회수: {}", articleView.getId(), article.getViewCount());
                    return articleView;
                });
        log.info("조회수 증가X, 현재 조회수: {}", article.getViewCount());

        // 날짜 포맷팅 (2025.09.19 형태로 변환)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        String formattedPubDate = article.getPubDate().format(formatter);

        // 3. 마인드맵 계층 구조 조회
        Map<String, Object> mindmapHierarchy = mindmapService.getMindmapHierarchy(userId, articleId);

        // MindmapDto로 변환
        MindmapDto mindmapDto = new MindmapDto(mindmapHierarchy);

        // TODO 4. 형광펜 및 메모 내역 반환
        HighlightDto highlightDto = new HighlightDto("highlight");

        // 5. ArticleDto로 변환
        ArticleDto articleDto = new ArticleDto(
                article.getId(),
                article.getTitle(),
                formattedPubDate,
                article.getSource(),
                article.getSummary(),
                article.getArticleLink(),
                article.getViewCount(),
                article.getImgUrl()
        );

        // 6. ArticleDetailResponseDto 반환
        ArticleDetailResponseDto responseDto = new ArticleDetailResponseDto(articleDto, mindmapDto, highlightDto);

        return ResponseEntity.ok(ApiResponse.success(responseDto, "기사 상세 조회 성공"));
    }
}
