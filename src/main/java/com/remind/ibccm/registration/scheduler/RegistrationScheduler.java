package com.remind.ibccm.registration.scheduler;

import com.remind.ibccm.registration.repository.RegistrationDeviceInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.Duration;
import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
public class RegistrationScheduler {

    private final Duration delay;

    private final RegistrationDeviceInfoRepository repository;

    @Scheduled(cron = "*/5 * * * * ?")
    @SchedulerLock(name = "RegistrationScheduler", lockAtLeastFor = "PT15S", lockAtMostFor = "PT30S")
    public void process() {

        LocalDateTime now = LocalDateTime.now();

        int result = repository.batchUpdate(now.minus(delay));

        log.info("finished scheduledTask, updated: {}", result);
    }
}
