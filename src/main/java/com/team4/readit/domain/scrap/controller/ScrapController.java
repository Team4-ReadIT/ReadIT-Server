package com.team4.readit.domain.scrap.controller;


import com.team4.readit.domain.scrap.service.ScrapService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class ScrapController {
    private final ScrapService scrapService;
}
