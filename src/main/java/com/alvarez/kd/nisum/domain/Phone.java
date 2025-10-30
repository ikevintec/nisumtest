package com.alvarez.kd.nisum.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Phone {

    private Long id;

    private String number;
    private String cityCode;
    private String countryCode;
    

}
