package com.team4.readit.global.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import jakarta.annotation.PostConstruct;
import java.io.File;

@Slf4j
@Configuration
public class PythonConfig {

    @Value("${python.script.path}")
    private String pythonScriptPath;

    @PostConstruct
    public void validatePythonEnvironment() {
        log.info("Validating Python script at path: {}", pythonScriptPath);
        File scriptFile = new File(pythonScriptPath);
        if (!scriptFile.exists()) {
            log.error("Python script not found at: {}", scriptFile.getAbsolutePath());
            throw new RuntimeException("Python script not found: " + pythonScriptPath);
        }
        log.info("Python script successfully validated at: {}", scriptFile.getAbsolutePath());

        // Python 실행 가능 여부 체크
        try {
            ProcessBuilder pb = new ProcessBuilder("python", "--version");
            Process process = pb.start();
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                log.info("Python is available in the system");
            } else {
                log.error("Python check failed with exit code: {}", exitCode);
            }
        } catch (Exception e) {
            log.error("Error checking Python availability", e);
        }
    }
}