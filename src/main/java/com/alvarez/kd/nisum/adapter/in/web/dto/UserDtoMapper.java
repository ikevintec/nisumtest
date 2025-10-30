package com.alvarez.kd.nisum.adapter.in.web.dto;

import com.alvarez.kd.nisum.domain.Phone;
import com.alvarez.kd.nisum.domain.User;

import java.util.stream.Collectors;

public class UserDtoMapper {

    public static User dtoToDomain(UserRequest userRequest) {
        User user = User.builder()
                .email(userRequest.getEmail())
                .name(userRequest.getName())
                .password(userRequest.getPassword())
                .phones(userRequest.getPhones().stream()
                                   .map(phoneRequest -> Phone.builder()
                                           .countryCode(phoneRequest.getCountryCode())
                                           .number(phoneRequest.getNumber())
                                           .cityCode(phoneRequest.getCityCode())
                                                             .build())
                                   .collect(Collectors.toList()))
                        .build();
        return user;
    }

    public static UserResponse domainToDto(User user) {
        UserResponse userResponse = UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .isActive(user.isActive())
                .lastLogin(user.getLastLogin())
                .createdAt(user.getCreatedAt())
                .lastModifiedAt(user.getLastModifiedAt())
                .token(user.getToken())
                .phones(user.getPhones().stream()
                                .map(phone -> PhoneResponse.builder()
                                                   .id(phone.getId())
                                                   .countryCode(phone.getCountryCode())
                                                   .number(phone.getNumber())
                                                   .cityCode(phone.getCityCode())
                                                   .build())
                            .collect(Collectors.toList()))
                                          .build();
        return userResponse;
    }
}
