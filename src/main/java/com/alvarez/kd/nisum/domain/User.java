package com.alvarez.kd.nisum.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    
    private Long id;

    private String name;
    private String email;
    private String password;
    
    private List<Phone> phones;

    private UUID token;

    private boolean isActive;

    private LocalDateTime lastLogin;

    private LocalDateTime createdAt;

    private LocalDateTime lastModifiedAt;

}
