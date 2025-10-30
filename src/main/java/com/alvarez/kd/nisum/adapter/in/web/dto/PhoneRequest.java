package com.alvarez.kd.nisum.adapter.in.web.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class PhoneRequest {

    @NotNull(message = "The phone number field is required")
    private String number;
    @NotNull(message = "The phone cityCode field is required")
    private String cityCode;
    @NotNull(message = "The phone countryCode field is required")
    private String countryCode;
    

}
