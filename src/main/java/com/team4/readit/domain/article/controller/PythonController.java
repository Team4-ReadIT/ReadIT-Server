package com.team4.readit.domain.article.controller;

import com.team4.readit.domain.article.domain.Article;
import com.team4.readit.domain.article.service.ArticleHelperService;
import com.team4.readit.domain.article.service.ArticleService;
import com.team4.readit.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;


@RestController
@RequiredArgsConstructor
public class PythonController {

    private final ArticleHelperService articleHelperService;
    private final ArticleService articleService;

    @GetMapping("/api/v1/articles/{articleId}/similar")
    public ResponseEntity<?> executePythonScript(@PathVariable Long articleId) {
        try {
            Article article = articleHelperService.getArticleById(articleId);
            String pythonScriptPath = "src/scripts/clustering.py";
            System.out.println("Received input articleId: " + articleId);

            ProcessBuilder processBuilder = new ProcessBuilder("python3", pythonScriptPath, articleId.toString());
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            // 인코딩을 명시적으로 설정하여 출력 읽기
            InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(inputStreamReader);

            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line);
            }

            // Python 스크립트가 반환한 JSON 데이터를 SimilarDto 리스트로 변환
            String outputStr = output.toString();
            System.out.println("Python script output: " + outputStr);

            // convertJsonToDtoList 메서드 호출
            return ResponseEntity.ok(ApiResponse.success(articleService.convertJsonToDtoList(outputStr), "유사한 기사 조회 성공"));

        } catch (IOException e) {
            throw new RuntimeException("Error executing Python script", e);
        }
    }
}
