package com.team4.readit.domain.clustering.controller;

import com.team4.readit.domain.clustering.domain.DataPoint;
import com.team4.readit.domain.clustering.dto.ClusteringRequest;
import com.team4.readit.domain.clustering.dto.ClusteringResponse;
import com.team4.readit.domain.clustering.service.ClusteringService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/clustering")
@RequiredArgsConstructor
public class ClusteringController {
    private final ClusteringService clusteringService;

    @PostMapping("/analyze")
    public ResponseEntity<ClusteringResponse> analyze(@RequestBody ClusteringRequest request) {
        return ResponseEntity.ok(clusteringService.analyze(request));
    }

    @GetMapping("/test")
    public ResponseEntity<ClusteringResponse> test() {
        ClusteringRequest testRequest = new ClusteringRequest();
        testRequest.setAlgorithm("test");
        testRequest.setDataPoints(Collections.emptyList());
        testRequest.setParameters(Collections.emptyMap());

        return ResponseEntity.ok(clusteringService.analyze(testRequest));
    }
}