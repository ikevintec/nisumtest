package com.alvarez.kd.nisum.adapter.in.web.dto;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class UserRequest {

    @NotNull(message = "The name field is required")
    private String name;
    @Email(message = "The email is incorrect")
    @NotNull(message = "The email field is required")
    private String email;

    @NotNull(message = "The password field is required")
    private String password;

    @NotNull(message = "The phones field is required")
    private List<PhoneRequest> phones;

}
