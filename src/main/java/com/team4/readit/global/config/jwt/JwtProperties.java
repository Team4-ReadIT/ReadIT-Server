package com.team4.readit.global.config.jwt;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    private String secret;
    private long accessTokenValidityInSeconds;

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public void setAccessTokenValidityInSeconds(long accessTokenValidityInSeconds) {
        this.accessTokenValidityInSeconds = accessTokenValidityInSeconds;
    }
}