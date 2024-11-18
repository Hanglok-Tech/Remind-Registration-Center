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
public class HouseKeepingScheduler {

    private final Duration delay;

    private final RegistrationDeviceInfoRepository repository;

    @Scheduled(cron = "*/3 * * * * ?")
    @SchedulerLock(name = "HouseKeepingScheduler", lockAtLeastFor = "PT15S", lockAtMostFor = "PT30S")
    public void process() {

        LocalDateTime now = LocalDateTime.now();

        int result = repository.batchHouseKeeping(now.minus(delay));

        log.info("finished house keeping scheduler, updated: {}", result);
    }
}
