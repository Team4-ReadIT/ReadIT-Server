package com.team4.readit.domain.user_info.service;

import com.team4.readit.domain.user_info.domain.repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserInfoService {
    private final UserInfoRepository userInfoRepository;
}
