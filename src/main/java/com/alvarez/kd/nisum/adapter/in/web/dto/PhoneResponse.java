package com.alvarez.kd.nisum.adapter.in.web.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PhoneResponse {

    private Long id;

    private String number;
    private String cityCode;
    private String countryCode;
    

}
