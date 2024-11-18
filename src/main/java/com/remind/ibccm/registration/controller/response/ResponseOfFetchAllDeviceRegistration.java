package com.remind.ibccm.registration.controller.response;

import com.remind.ibccm.registration.entity.RegistrationInfo;
import com.remind.ibccm.registration.utils.DateTimeUtils;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class ResponseOfFetchAllDeviceRegistration {

    private String remindType;

    private String remindSubType;

    private String remindUuid;

    private String deviceName;

    private String serviceIp;

    private Integer servicePort;

    private String status;

    private String createdAt;

    private String updatedAt;

    private String createdBy;

    private String updatedBy;

    public static ResponseOfFetchAllDeviceRegistration convert(RegistrationInfo registrationInfo) {

        ResponseOfFetchAllDeviceRegistration result = new ResponseOfFetchAllDeviceRegistration();

        BeanUtils.copyProperties(registrationInfo, result, "createdAt", "updatedAt");

        result.setStatus(registrationInfo.getStatus());
        result.setCreatedAt(DateTimeUtils.convert(registrationInfo.getCreatedAt()));
        result.setUpdatedAt(DateTimeUtils.convert(registrationInfo.getUpdatedAt()));

        return result;
    }

}
