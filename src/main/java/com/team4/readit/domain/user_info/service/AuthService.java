package com.team4.readit.domain.user_info.service;

import com.team4.readit.global.config.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.team4.readit.domain.job.domain.Job;
import com.team4.readit.domain.job.domain.repository.JobRepository;
import com.team4.readit.domain.user_info.domain.UserInfo;
import com.team4.readit.domain.user_info.domain.repository.UserInfoRepository;
import com.team4.readit.domain.user_info.dto.AuthDto;
import com.team4.readit.global.exception.BadRequestException;
import com.team4.readit.global.exception.ExceptionCode;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final UserInfoRepository userInfoRepository;
    private final JobRepository jobRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public Long signup(AuthDto.SignUpRequest request) {
        if (userInfoRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException(ExceptionCode.EMAIL_ALREADY_EXISTS);
        }

        Job job = jobRepository.findById(request.getJobId())
                .orElseThrow(() -> new BadRequestException(ExceptionCode.INVALID_JOB));

        UserInfo userInfo = UserInfo.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))  // 비밀번호 암호화
                .name(request.getName())
                .job(job)
                .build();

        UserInfo savedUser = userInfoRepository.save(userInfo);
        return savedUser.getId();
    }

    public AuthDto.LoginResponse login(AuthDto.LoginRequest request) {
        UserInfo userInfo = userInfoRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadRequestException(ExceptionCode.LOGIN_FAILED));

        if (!passwordEncoder.matches(request.getPassword(), userInfo.getPassword())) {
            throw new BadRequestException(ExceptionCode.LOGIN_FAILED);
        }

        String token = jwtTokenProvider.createToken(userInfo.getEmail());

        AuthDto.UserInfoResponse userInfoResponse = new AuthDto.UserInfoResponse(
                userInfo.getId(),
                userInfo.getName(),
                userInfo.getEmail(),
                userInfo.getJob().getJobName()
        );

        return new AuthDto.LoginResponse(token, userInfoResponse);
    }
}