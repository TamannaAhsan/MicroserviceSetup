package com.tamanna.authserver.security.jwt;

import io.jsonwebtoken.security.Keys;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

@Data
@Configuration
public class JwtProperties {
    @Bean
    public SecretKey secretKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    private String secretKey = "SomeSecretKeyWhichIsVeryLongAndSecureItWillBeUsedForJWY";
}
