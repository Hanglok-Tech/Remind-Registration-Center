package com.remind.ibccm.registration.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class RequestOfRegister {

    @NotEmpty(message = "serviceIp should not be empty")
    @JsonProperty("serviceIp")
    private String serviceIp;

    @JsonProperty("servicePort")
    private Integer servicePort;

    @NotEmpty(message = "deviceName should not be empty")
    @JsonProperty("deviceName")
    private String deviceName;

    @NotEmpty(message = "deviceType should not be empty")
    @JsonProperty("remindType")
    private String remindType;

    private String remindSubType;

    @NotEmpty(message = "status should not be empty")
    private String status;

    @NotEmpty(message = "remindUuid should not be empty")
    private String remindUuid;

}
