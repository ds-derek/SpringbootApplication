package com.example.app;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("jwt")
@Getter
@Setter
public class JwtProperties {
    private String header;
    private String base64Secret;
    private String tokenValidityInSeconds;
    private String tokenValidityInSecondsForRememberMe;
}
