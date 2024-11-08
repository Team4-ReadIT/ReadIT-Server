package com.team4.readit.domain.clustering.service;

import com.fasterxml.jackson.core.JsonParser;
import com.team4.readit.domain.clustering.dto.ClusteringRequest;
import com.team4.readit.domain.clustering.dto.ClusteringResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Slf4j
@Service
public class ClusteringService {

    @Value("${python.script.path}")
    private String pythonScriptPath;

    private final ObjectMapper objectMapper;

    public ClusteringService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        log.info("ClusteringService initialized with script path: {}", pythonScriptPath);
    }

    public ClusteringResponse analyze(ClusteringRequest request) {
        try {
            log.info("Starting analysis with request: {}", request);
            String jsonInput = objectMapper.writeValueAsString(request);
            log.info("JSON input prepared: {}", jsonInput);

            File scriptFile = new File(pythonScriptPath);
            if (!scriptFile.exists()) {
                throw new RuntimeException("Python script not found at: " + pythonScriptPath);
            }
            log.info("Python script found at: {}", pythonScriptPath);

            ProcessBuilder pb = new ProcessBuilder("python", pythonScriptPath, jsonInput);

            // 환경 변수 설정
            pb.environment().put("PYTHONIOENCODING", "utf-8");
            pb.environment().put("LANG", "ko_KR.UTF-8");

            pb.redirectErrorStream(true);

            Process process = pb.start();
            log.info("Python process started");

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {

                StringBuilder output = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line);
                    log.info("Python output: {}", line);
                }

                int exitCode = process.waitFor();
                if (exitCode != 0) {
                    throw new RuntimeException("Python script failed with exit code: " + exitCode);
                }

                // ObjectMapper에 UTF-8 설정
                objectMapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
                return objectMapper.readValue(output.toString().getBytes(StandardCharsets.UTF_8),
                        ClusteringResponse.class);
            }

        } catch (Exception e) {
            log.error("Clustering analysis failed", e);
            throw new RuntimeException("Failed to perform clustering analysis", e);
        }
    }
}