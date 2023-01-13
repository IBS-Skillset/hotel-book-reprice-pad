package com.hotel.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("rsa")
public class RSAConfiguration {
    private String mod;
    private String publicExp;
    private String privateExp;
}
