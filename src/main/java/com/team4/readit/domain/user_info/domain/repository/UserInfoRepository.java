package com.team4.readit.domain.user_info.domain.repository;

import com.team4.readit.domain.user_info.domain.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
}