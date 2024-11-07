package com.team4.readit.domain.highlight.service;

import com.team4.readit.domain.highlight.domain.Highlight;
import com.team4.readit.domain.highlight.domain.repository.HighlightRepository;
import com.team4.readit.domain.highlight.dto.response.HighlightDto;
import com.team4.readit.global.converter.HighlightDtoConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HighlightService {
    private final HighlightRepository highlightRepository;

    public List<HighlightDto> getHighlightsByArticleAndUser(Long articleId, Long userId) {

        List<Highlight> highlights = highlightRepository.findAllByArticleIdAndUserId(articleId, userId);
        return highlights.stream()
                .map(HighlightDtoConverter::convertToHighlightDto)
                .collect(Collectors.toList());
    }
}
