package com.ecommerce.shopity.config;


import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "shopity")
@Data
public class ApplicationProperties {

    private final String name;
}
