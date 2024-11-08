package com.team4.readit.domain.user_info.domain.repository;

import com.team4.readit.domain.user_info.domain.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
    Optional<UserInfo> findByEmail(String email);
    boolean existsByEmail(String email);
}