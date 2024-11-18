package com.remind.ibccm.registration.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "registration.house-keeping.scheduler")
public class SchedulerForHouseKeepingConfigProperties {

    private String delay;

}
