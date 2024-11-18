package com.remind.ibccm.registration.service.impl;

import com.remind.ibccm.registration.config.RegistrationConcurrentProperties;
import com.remind.ibccm.registration.controller.request.RequestOfRegister;
import com.remind.ibccm.registration.entity.RegistrationInfo;
import com.remind.ibccm.registration.repository.RegistrationDeviceInfoRepository;
import com.remind.ibccm.registration.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.StreamSupport;

@Slf4j
@RequiredArgsConstructor
public class DefaultRegistrationService implements RegistrationService {

    private final RegistrationDeviceInfoRepository repository;

    private final RegistrationConcurrentProperties concurrentProperties;

    @Override
    public List<RegistrationInfo> fetchAll() {

        return StreamSupport
                .stream(repository.findAll().spliterator(), false)
                .toList();
    }

    @Override
    @Transactional
    public List<RegistrationInfo> register(RequestOfRegister request) {

        List<RegistrationInfo> registrationInfoList = repository.findByRemindTypeAndRemindUuidOrderByUpdatedAtDesc(request.getRemindType(), request.getRemindUuid());

        if (registrationInfoList.size() > 1) {
            log.error("case should never happen, registrationInfoList: {}, need check!", registrationInfoList);
            throw new RuntimeException("case should never happen, registrationInfoList");
        }

        RegistrationInfo registrationInfo = registrationInfoList.isEmpty() ? createRegistration(request) : updateRegistration(registrationInfoList.getFirst(), request);

        repository.save(registrationInfo);

        List<RegistrationInfo> registrationInfoWithSameType = repository.findByRemindTypeAndStatusOrderByUpdatedAtDesc(request.getRemindType(), RegistrationInfo.RegistrationStatus.ACTIVE.getShortName());

        sanityCheck(registrationInfoWithSameType.size());

        return repository.findByStatusOrderByUpdatedAtDesc(RegistrationInfo.RegistrationStatus.ACTIVE.getShortName());

    }

    private RegistrationInfo updateRegistration(RegistrationInfo first, RequestOfRegister request) {
        first.update(request);
        return first;
    }

    private void sanityCheck(int dbSize) {
        if (dbSize > concurrentProperties.getMaxAllowedConcurrent()) {
            throw new RuntimeException("For same type, maximum allowed concurrent service is " + concurrentProperties.getMaxAllowedConcurrent());
        }
    }

    private RegistrationInfo createRegistration(RequestOfRegister request) {
        return RegistrationInfo.create(request);
    }

}
