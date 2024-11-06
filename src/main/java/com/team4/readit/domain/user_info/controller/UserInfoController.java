package com.team4.readit.domain.user_info.controller;

import com.team4.readit.domain.user_info.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class UserInfoController {
    private final UserInfoService userInfoService;
}
