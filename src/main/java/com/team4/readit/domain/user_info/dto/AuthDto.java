package com.team4.readit.domain.user_info.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class AuthDto {
    @Getter
    @NoArgsConstructor
    public static class SignUpRequest {
        @NotBlank(message = "이름은 필수입니다.")
        private String name;

        @NotBlank(message = "이메일은 필수입니다.")
        @Email(message = "올바른 이메일 형식이 아닙니다.")
        private String email;

        @NotBlank(message = "비밀번호는 필수입니다.")
//        @Size(min = 8, message = "비밀번호는 8자 이상이어야 합니다.")
        private String password;

        @NotNull(message = "직무는 필수입니다")  // @NotBlank 대신 @NotNull 사용
        private Long jobId;
    }

    @Getter
    @NoArgsConstructor
    public static class LoginRequest {
        @NotBlank(message = "이메일은 필수입니다.")
        @Email(message = "올바른 이메일 형식이 아닙니다.")
        private String email;

        @NotBlank(message = "비밀번호는 필수입니다.")
        private String password;
    }

    @Getter
    @NoArgsConstructor
    public static class UserInfoResponse {
        private Long id;
        private String name;
        private String email;
        private String jobName;

        public UserInfoResponse(Long id, String name, String email, String jobName) {
            this.id = id;
            this.name = name;
            this.email = email;
            this.jobName = jobName;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class LoginResponse {
        private String token;
        private UserInfoResponse userInfo;

        public LoginResponse(String token, UserInfoResponse userInfo) {
            this.token = token;
            this.userInfo = userInfo;
        }
    }
}