package com.alvarez.kd.nisum.adapter.in.web.dto;


import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
    
    private Long id;

    private String name;
    private String email;
    
    private List<PhoneResponse> phones;

    private UUID token;

    private boolean isActive;

    private LocalDateTime lastLogin;

    private LocalDateTime createdAt;

    private LocalDateTime lastModifiedAt;

}
