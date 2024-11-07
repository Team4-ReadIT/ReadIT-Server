package com.team4.readit.domain.scrap.service;

import com.team4.readit.domain.scrap.domain.repository.ScrapRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ScrapHelperService {
    private final ScrapRepository scrapRepository;

    public boolean isScraped(Long userId, Long articleId) {
        return scrapRepository.existsByUserIdAndArticleId(userId, articleId);
    }
}
