package com.team4.readit.domain.user_info.service;

import com.team4.readit.domain.user_info.domain.UserInfo;
import com.team4.readit.domain.user_info.domain.repository.UserInfoRepository;
import com.team4.readit.domain.user_info.dto.AuthDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.team4.readit.global.exception.InvalidInputException;
import com.team4.readit.global.exception.ExceptionCode;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserInfoService {
    private final UserInfoRepository userInfoRepository;

    public AuthDto.UserInfoResponse getUserInfo(String email) {
        UserInfo userInfo = userInfoRepository.findByEmail(email)
                .orElseThrow(() -> new InvalidInputException(ExceptionCode.INVALID_USER));

        return new AuthDto.UserInfoResponse(
                userInfo.getId(),
                userInfo.getName(),
                userInfo.getEmail(),
                userInfo.getJob().getJobName()
        );
    }
}