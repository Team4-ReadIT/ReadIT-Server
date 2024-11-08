package com.team4.readit.domain.user_info.service;

import com.team4.readit.domain.job.domain.Job;
import com.team4.readit.domain.job.domain.repository.JobRepository;
import com.team4.readit.domain.user_info.domain.UserInfo;
import com.team4.readit.domain.user_info.domain.repository.UserInfoRepository;
import com.team4.readit.domain.user_info.dto.AuthDto;
import com.team4.readit.global.exception.BadRequestException;
import com.team4.readit.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.team4.readit.global.exception.InvalidInputException;
import com.team4.readit.global.exception.ExceptionCode;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
    private final UserInfoRepository userInfoRepository;
    private final JobRepository jobRepository;

    public Long signup(AuthDto.SignUpRequest request) {
        // Check if email already exists
        if (userInfoRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException(ExceptionCode.EMAIL_ALREADY_EXISTS);
        }

        Job job = jobRepository.findById(request.getJobId())
                .orElseThrow(() -> new BadRequestException(ExceptionCode.INVALID_JOB));

        UserInfo userInfo = UserInfo.builder()
                .email(request.getEmail())
                .password(request.getPassword())  // In real application, password should be encoded
                .name(request.getName())
                .job(job)
                .build();

        UserInfo savedUser = userInfoRepository.save(userInfo);
        return savedUser.getId();
    }

    public AuthDto.UserInfoResponse login(AuthDto.LoginRequest request) {
        UserInfo userInfo = userInfoRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadRequestException(ExceptionCode.LOGIN_FAILED));

        if (!userInfo.matchPassword(request.getPassword())) {
            throw new BadRequestException(ExceptionCode.LOGIN_FAILED);
        }

        return new AuthDto.UserInfoResponse(
                userInfo.getId(),
                userInfo.getName(),
                userInfo.getEmail(),
                userInfo.getJob().getJobName()
        );
    }
}