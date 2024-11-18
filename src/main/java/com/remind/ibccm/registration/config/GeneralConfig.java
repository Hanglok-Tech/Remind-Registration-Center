package com.remind.ibccm.registration.config;

import com.remind.ibccm.registration.repository.RegistrationDeviceInfoRepository;
import com.remind.ibccm.registration.scheduler.HouseKeepingScheduler;
import com.remind.ibccm.registration.scheduler.RegistrationScheduler;
import com.remind.ibccm.registration.service.RegistrationService;
import com.remind.ibccm.registration.service.impl.DefaultRegistrationService;
import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.provider.jdbctemplate.JdbcTemplateLockProvider;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.time.Duration;

@Configuration
@EnableConfigurationProperties({SchedulerForHealthCheckConfigProperties.class, RegistrationConcurrentProperties.class, SchedulerForHouseKeepingConfigProperties.class})
public class GeneralConfig {
    @Bean
    public LockProvider lockProvider(DataSource dataSource) {
        return new JdbcTemplateLockProvider(dataSource);
    }

    @Bean
    public RegistrationScheduler registrationScheduler(SchedulerForHealthCheckConfigProperties schedulerForHealthCheckConfigProperties, RegistrationDeviceInfoRepository repository) {

        Duration delay = Duration.parse(schedulerForHealthCheckConfigProperties.getDelay());

        return new RegistrationScheduler(delay, repository);
    }

    @Bean
    public HouseKeepingScheduler houseKeepingScheduler(SchedulerForHouseKeepingConfigProperties schedulerForHouseKeepingConfigProperties, RegistrationDeviceInfoRepository repository) {

        Duration delay = Duration.parse(schedulerForHouseKeepingConfigProperties.getDelay());

        return new HouseKeepingScheduler(delay, repository);
    }

    @Bean
    public RegistrationService registrationService(RegistrationDeviceInfoRepository repository, RegistrationConcurrentProperties concurrentProperties) {

        return new DefaultRegistrationService(repository, concurrentProperties);
    }
}
