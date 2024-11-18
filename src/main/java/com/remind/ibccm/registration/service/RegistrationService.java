package com.remind.ibccm.registration.service;

import com.remind.ibccm.registration.controller.request.RequestOfRegister;
import com.remind.ibccm.registration.entity.RegistrationInfo;

import java.util.List;

public interface RegistrationService {

    List<RegistrationInfo> fetchAll();

    List<RegistrationInfo> register(RequestOfRegister request);
}
