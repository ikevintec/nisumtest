package com.alvarez.kd.nisum.adapter.in.web;

import com.alvarez.kd.nisum.adapter.in.web.dto.UserDtoMapper;
import com.alvarez.kd.nisum.adapter.in.web.dto.UserRequest;
import com.alvarez.kd.nisum.adapter.in.web.dto.UserResponse;
import com.alvarez.kd.nisum.application.port.in.UserPort;
import com.alvarez.kd.nisum.common.WebAdapter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

@WebAdapter
@RestController
@RequestMapping("v1/users")
public class UserController {

    private final UserPort userPort;

    public UserController(UserPort userPort) {
        this.userPort = userPort;
    }


    @GetMapping
    ResponseEntity<List<UserResponse>> findAll() {
        return ResponseEntity.ok().body(
                userPort.findAll().stream().map(UserDtoMapper::domainToDto).collect(
                        Collectors.toList()));
    }

    @PostMapping
    ResponseEntity<UserResponse> save(
            @Valid @RequestBody UserRequest userRequest) {
        return new ResponseEntity<>(
                UserDtoMapper.domainToDto(userPort.create(UserDtoMapper.dtoToDomain(userRequest))),
                HttpStatus.CREATED);
    }

    @PostMapping("/{id}/disable")
    void disable(
            @Valid @PathVariable Long id) {
        userPort.disable(id);
    }


}
