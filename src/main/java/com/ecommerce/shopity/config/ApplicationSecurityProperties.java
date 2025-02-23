package com.ecommerce.shopity.config;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@ConfigurationProperties(prefix = "app.security")
@Data
public class ApplicationSecurityProperties {

    private Integer jwtExpirationMs;
    private String jwtSecret;
}
