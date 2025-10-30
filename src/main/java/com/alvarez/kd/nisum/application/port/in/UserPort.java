package com.alvarez.kd.nisum.application.port.in;

import com.alvarez.kd.nisum.domain.User;

import java.util.List;

public interface UserPort {

    List<User> findAll();
    User create(User user);
    
    void disable(Long id);
}
