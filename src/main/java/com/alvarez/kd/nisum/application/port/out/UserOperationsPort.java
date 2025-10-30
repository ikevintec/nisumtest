package com.alvarez.kd.nisum.application.port.out;

import com.alvarez.kd.nisum.domain.User;

import java.util.List;

public interface UserOperationsPort {
    List<User> findAll();
    User save(User user);
    
    void disable(Long id);

    Boolean existsByEmail(String email);

}
