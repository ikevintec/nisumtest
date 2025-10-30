package com.alvarez.kd.nisum.domain.exceptions;

import lombok.Data;

@Data
public class ApiErrorResponse {
    private final String errorCode;
    private final String message;
}
