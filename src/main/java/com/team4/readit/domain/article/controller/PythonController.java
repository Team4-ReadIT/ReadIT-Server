package com.team4.readit.domain.article.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@RestController
public class PythonController {

    @GetMapping("/api/model/execute-python")
    public void executePythonScript(@RequestParam String arg1, @RequestParam String arg2) {
        try {
            String pythonScriptPath = "src/scripts/model.py";
            ProcessBuilder processBuilder = new ProcessBuilder("python", pythonScriptPath, "arg1", "agr2");
            Process process = processBuilder.start();

            InputStream inputStream = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // 실행 결과 처리
                System.out.println(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
