package com.remind.ibccm.registration.entity;

import com.remind.ibccm.registration.controller.request.RequestOfRegister;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Slf4j
@Table("registration_info")
@Data
public class RegistrationInfo {

    @Id
    private Integer id;

    private String remindType;

    private String remindSubType;

    private String deviceName;

    private String remindUuid;

    private String serviceIp;

    private Integer servicePort;

    private String status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String createdBy;

    private String updatedBy;

    public void update(RequestOfRegister request) {
        this.setServiceIp(request.getServiceIp());
        this.setServicePort(request.getServicePort());
        this.setRemindSubType(request.getRemindSubType());
        this.setDeviceName(request.getDeviceName());
        this.setStatus(RegistrationStatus.convert(request.getStatus()).getShortName());
        this.setUpdatedAt(LocalDateTime.now());
        this.setUpdatedBy(this.getRemindType());
    }

    @Getter
    public enum RegistrationStatus {
        ACTIVE("a"),
        EXCEPTION("e"),
        INACTIVE("i"),
        NA("na"),
        ;

        private final String shortName;

        RegistrationStatus(String shortName) {
            this.shortName = shortName;
        }

        public static RegistrationStatus convert(String input) {
            if (StringUtils.isEmpty(input)) {
                return NA;
            }

            for (RegistrationStatus value : RegistrationStatus.values()) {
                if (value.getShortName().equalsIgnoreCase(input)) {
                    return value;
                }
            }

            return NA;
        }
    }

    public static RegistrationInfo create(RequestOfRegister request) {
        RegistrationInfo registrationInfo = new RegistrationInfo();

        BeanUtils.copyProperties(request, registrationInfo);

        LocalDateTime now = LocalDateTime.now();

        registrationInfo.setUpdatedAt(now);
        registrationInfo.setCreatedAt(now);
        registrationInfo.setCreatedBy(request.getRemindType());
        registrationInfo.setUpdatedBy(request.getRemindType());
        registrationInfo.setRemindUuid(request.getRemindUuid());

        registrationInfo.setStatus(RegistrationStatus.ACTIVE.getShortName());

        return registrationInfo;
    }

}
