package com.team4.readit.domain.mindmap.service;

import com.team4.readit.domain.mindmap.domain.repository.MindmapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MindmapService {
    private final MindmapRepository mindmapRepository;
}
