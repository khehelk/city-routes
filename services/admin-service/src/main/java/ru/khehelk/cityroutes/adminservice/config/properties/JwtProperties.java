package ru.khehelk.cityroutes.adminservice.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("jwt")
public class JwtProperties {

    private String secretBase64;

    private long expirationInMs;

}
