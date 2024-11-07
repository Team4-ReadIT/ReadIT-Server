package com.team4.readit.domain.user_info.service;

import com.team4.readit.domain.user_info.domain.UserInfo;
import com.team4.readit.domain.user_info.domain.repository.UserInfoRepository;
import com.team4.readit.global.exception.ExceptionCode;
import com.team4.readit.global.exception.InvalidInputException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserInfoUtil {

    private final UserInfoRepository userInfoRepository;

    // TODO userId 대신 email로 유저 정보 가져오는 것으로 변경
    public UserInfo getUserInfoById(Long userId) {
        return userInfoRepository.findById(userId)
                .orElseThrow(() -> new InvalidInputException(ExceptionCode.INVALID_USER));
    }
}
