package com.team4.readit.domain.user_info.controller;

import com.team4.readit.domain.user_info.dto.AuthDto;
import com.team4.readit.domain.user_info.service.UserInfoService;
import com.team4.readit.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class UserInfoController {
    private final UserInfoService userInfoService;

    @GetMapping("/info")
    public ApiResponse<AuthDto.UserInfoResponse> getUserInfo(@RequestParam String email) {
        AuthDto.UserInfoResponse response = userInfoService.getUserInfo(email);
        return ApiResponse.success(response, "사용자 정보 조회가 완료되었습니다.");
    }
}