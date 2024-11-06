package com.team4.readit.domain.keyword.service;

import com.team4.readit.domain.keyword.domain.repository.KeywordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class KeywordService {
    private final KeywordRepository keywordRepository;
}
