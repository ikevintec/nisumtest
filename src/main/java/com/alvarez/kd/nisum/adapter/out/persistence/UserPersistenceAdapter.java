package com.alvarez.kd.nisum.adapter.out.persistence;

import com.alvarez.kd.nisum.application.port.out.UserOperationsPort;
import com.alvarez.kd.nisum.common.PersistenceAdapter;
import com.alvarez.kd.nisum.domain.User;

import java.util.List;
import java.util.stream.Collectors;

@PersistenceAdapter
public class UserPersistenceAdapter implements UserOperationsPort {

    private final UserRepository userRepository;

    public UserPersistenceAdapter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll().stream().map(UserEntityMapper::entityToDomain)
                             .collect(Collectors.toList());
    }

    @Override
    public User save(User user) {
        return UserEntityMapper.entityToDomain(userRepository.save(
                UserEntityMapper.domainToEntity(user)));
    }

    @Override
    public void disable(Long id) {
        userRepository.findById(id).ifPresent(userEntity -> {
            userEntity.setActive(false);
            userRepository.save(userEntity);
        });
    }

    @Override
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
