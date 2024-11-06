package com.team4.readit.domain.article.service;

import com.team4.readit.domain.article.domain.Article;
import com.team4.readit.domain.article.domain.repository.ArticleRepository;
import com.team4.readit.domain.article.dto.response.JobArticleResponseDTO;
import com.team4.readit.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;

    public ResponseEntity<?> getPopularArticlesByJob(Long jobId) {
        LocalDateTime oneWeekAgo = LocalDateTime.now().minusWeeks(1)
                .withHour(0)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);

        log.info("oneWeekAgo: {}", oneWeekAgo);

        List<Article> articles = articleRepository.findTop5PopularArticlesByJobId(jobId, oneWeekAgo);

        // Article -> PopularArticleResponseDTO로 변환
        List<JobArticleResponseDTO> articleDTOs = articles.stream()
                .map(article -> new JobArticleResponseDTO(
                        article.getId(),
                        article.getTitle(),
                        article.getImgUrl(),
                        article.getSource()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponse.success(articleDTOs, "사용자 직무의 인기 기사 조회 성공"));
    }
}
