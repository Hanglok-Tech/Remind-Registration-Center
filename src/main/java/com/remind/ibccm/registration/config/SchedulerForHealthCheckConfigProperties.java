package com.remind.ibccm.registration.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "registration.health-check.scheduler")
public class SchedulerForHealthCheckConfigProperties {

    private String delay;

}
