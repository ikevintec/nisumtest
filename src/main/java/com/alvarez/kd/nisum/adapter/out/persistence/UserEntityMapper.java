package com.alvarez.kd.nisum.adapter.out.persistence;

import com.alvarez.kd.nisum.domain.Phone;
import com.alvarez.kd.nisum.domain.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class UserEntityMapper {

    public static User entityToDomain(UserEntity userEntity) {
        User user = User.builder()
                .id(userEntity.getId())
                .email(userEntity.getEmail())
                .name(userEntity.getName())
                .password(userEntity.getPassword())
                .isActive(userEntity.isActive())
                .lastLogin(userEntity.getLastLogin())
                .createdAt(userEntity.getCreatedAt())
                .lastModifiedAt(userEntity.getLastModifiedAt())
                .token(userEntity.getToken())
                .phones(userEntity.getPhones().stream()
                                  .map(phoneEntity -> Phone.builder()
                                          .id(phoneEntity.getId())
                                          .countryCode(phoneEntity.getCountryCode())
                                          .number(phoneEntity.getNumber())
                                          .cityCode(phoneEntity.getCityCode())
                                                           .build()
                                      ).collect(Collectors.toList()))
                        .build();
        return user;
    }

    public static UserEntity domainToEntity(User user) {
        UserEntity userEntity = UserEntity.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .password(user.getPassword())
                .isActive(true)
                .lastLogin(LocalDateTime.now())
                .token(UUID.randomUUID()).build();
        List<PhoneEntity> phoneEntityList = user.getPhones().stream()
                                                .map(phoneEntity -> PhoneEntity.builder()
                                                                               .countryCode(phoneEntity.getCountryCode())
                                                                               .number(phoneEntity.getNumber())
                                                                               .cityCode(phoneEntity.getCityCode())
                                                                               .user(userEntity)
                                                                               .build()
                                                    ).collect(Collectors.toList());
        userEntity.setPhones(phoneEntityList);
        return userEntity;
    }
}
