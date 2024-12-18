package ru.khehelk.cityroutes.authservice.config.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@AllArgsConstructor
@ConfigurationProperties("jwt")
public class JwtProperties {

    private String secretBase64;

    private long expirationInMs;

}
