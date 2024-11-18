package com.remind.ibccm.registration.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "registration.concurrent")
public class RegistrationConcurrentProperties {

    private int maxAllowedConcurrent;
}
