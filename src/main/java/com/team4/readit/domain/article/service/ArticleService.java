package com.team4.readit.domain.article.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team4.readit.domain.article.domain.Article;
import com.team4.readit.domain.article.domain.repository.ArticleRepository;
import com.team4.readit.domain.highlight.dto.HighlightDto;
import com.team4.readit.domain.highlight.service.HighlightService;
import com.team4.readit.domain.scrap.service.ScrapHelperService;
import com.team4.readit.global.converter.ArticleDtoConverter;
import com.team4.readit.domain.article.dto.response.*;
import com.team4.readit.domain.article_view.service.ArticleViewService;
import com.team4.readit.domain.job.domain.Job;
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
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final UserInfoUtil userInfoUtil;
    private final ArticleViewService articleViewService;
    private final HighlightService highlightService;
    private final ScrapHelperService scrapHelperService;
    private final ArticleHelperService articleHelperService;
    public ResponseEntity<?> getTopArticlesByJob(String email) {

        UserInfo userInfo = userInfoUtil.getUserInfoByEmail(email);

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
    public ResponseEntity<?> getArticleDetail(String email, Long articleId) {
        Article article = articleHelperService.getArticleById(articleId);
        UserInfo userInfo = userInfoUtil.getUserInfoByEmail(email);
        Long userId = userInfo.getId();
        Job job = userInfo.getJob();

        // 사용자가 현재 직무에서 이전에 조회한 적이 없다면 조회수 증가
        // 사용자가 직무를 변경할 수 있으므로 조회수를 조회하는 기준에 job_id도 포함시켜야 함
        articleViewService.increaseViewCount(userInfo, job, article);

        // 사용자 스크랩 여부 조회
        boolean isScrapped = scrapHelperService.isScraped(userId, articleId);

        ArticleDto articleDto = ArticleDtoConverter.convertToArticleDto(article, isScrapped);

        // 하이라이트된 문장 조회
        List<HighlightDto> highlightDtos = highlightService.getHighlightsByArticleAndUser(articleId, userId);

        ArticleDetailResponseDto responseDto = ArticleDtoConverter.convertToArticleDetailResponseDto(articleDto, highlightDtos);

        return ResponseEntity.ok(ApiResponse.success(responseDto, "기사 상세 조회 성공"));
    }

    @Transactional(readOnly = true)
    public List<SimilarDto> convertJsonToDtoList(String outputStr) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            List<SimilarDto> similarDtos = objectMapper.readValue(outputStr, new TypeReference<List<SimilarDto>>() {});

            // id로 Article을 조회하고 imgurl과 source 설정
            similarDtos.forEach(similarDto -> {
                Article article = articleHelperService.getArticleById(similarDto.getId());
                similarDto.setImgUrl(article.getImgUrl());
                similarDto.setSource(article.getSource());
            });

            return similarDtos;
        } catch (IOException e) {
            throw new RuntimeException("Error parsing JSON to List<SimilarDto>", e);
        }
    }
}
