package com.team4.readit.domain.user_info.controller;

import com.team4.readit.domain.user_info.dto.AuthDto;
import com.team4.readit.domain.user_info.service.AuthService;
import com.team4.readit.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ApiResponse<Long> signup(@Valid @RequestBody AuthDto.SignUpRequest request) {
        Long userId = authService.signup(request);
        return ApiResponse.success(userId, "회원가입이 완료되었습니다.");
    }

    @PostMapping("/login")
    public ApiResponse<AuthDto.LoginResponse> login(@Valid @RequestBody AuthDto.LoginRequest request) {
        AuthDto.LoginResponse response = authService.login(request);
        return ApiResponse.success(response, "로그인이 완료되었습니다.");
    }
}