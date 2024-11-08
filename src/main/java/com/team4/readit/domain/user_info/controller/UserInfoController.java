package com.team4.readit.domain.user_info.controller;

import com.team4.readit.domain.user_info.dto.AuthDto;
import com.team4.readit.domain.user_info.service.UserInfoService;
import com.team4.readit.global.config.jwt.JwtTokenProvider;
import com.team4.readit.global.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserInfoController {
    private final UserInfoService userInfoService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/info")
    public ApiResponse<AuthDto.UserInfoResponse> getUserInfo(Authentication authentication) {
        AuthDto.UserInfoResponse response = userInfoService.getUserInfo(authentication.getName());
        return ApiResponse.success(response, "사용자 정보 조회가 완료되었습니다.");
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}