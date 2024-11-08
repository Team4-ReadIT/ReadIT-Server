package com.team4.readit.domain.scrap.controller;


import com.team4.readit.domain.scrap.service.ScrapService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/scraps")
public class ScrapController {
    private final ScrapService scrapService;

    @PostMapping("/{articleId}/toggle")
    public ResponseEntity<?> toggleScrap(Authentication authentication, @PathVariable Long articleId) {
        return scrapService.toggleScrap(authentication.getName(), articleId);
    }

    @GetMapping
    public ResponseEntity<?> getMyScraps(Authentication authentication) {
        return scrapService.getMyScraps(authentication.getName());
    }
}
