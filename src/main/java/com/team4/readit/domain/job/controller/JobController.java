package com.team4.readit.domain.job.controller;


import com.team4.readit.domain.job.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class JobController {
    private final JobService jobService;
}
