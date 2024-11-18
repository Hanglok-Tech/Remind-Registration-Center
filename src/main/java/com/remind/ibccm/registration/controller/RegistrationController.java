package com.remind.ibccm.registration.controller;

import com.remind.ibccm.registration.controller.request.RequestOfRegister;
import com.remind.ibccm.registration.controller.response.ResponseOfFetchAllDeviceRegistration;
import com.remind.ibccm.registration.entity.RegistrationInfo;
import com.remind.ibccm.registration.service.RegistrationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/registration")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @GetMapping(path = "/fetchAll")
    public ResponseEntity<List<ResponseOfFetchAllDeviceRegistration>> fetchAll() {

        List<RegistrationInfo> registrationInfoList = registrationService.fetchAll();

        List<ResponseOfFetchAllDeviceRegistration> result = registrationInfoList.stream()
                .map(ResponseOfFetchAllDeviceRegistration::convert)
                .toList();

        return ResponseEntity.ok(result);
    }

    @PostMapping("/register")
    public ResponseEntity<List<ResponseOfFetchAllDeviceRegistration>> register(@Validated @RequestBody RequestOfRegister request) {

        List<RegistrationInfo> activeDeviceList = registrationService.register(request);

        List<ResponseOfFetchAllDeviceRegistration> result = activeDeviceList.stream()
                .map(ResponseOfFetchAllDeviceRegistration::convert)
                .toList();

        return ResponseEntity.ok(result);

    }

}
