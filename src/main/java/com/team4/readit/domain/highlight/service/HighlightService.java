package com.team4.readit.domain.highlight.service;

import com.team4.readit.domain.highlight.domain.repository.HighlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HighlightService {
    private final HighlightRepository highlightRepository;
}
