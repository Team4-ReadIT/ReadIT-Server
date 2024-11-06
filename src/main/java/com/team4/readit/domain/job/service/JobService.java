package com.team4.readit.domain.job.service;

import com.team4.readit.domain.job.domain.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class JobService {
    private final JobRepository jobRepository;
}
