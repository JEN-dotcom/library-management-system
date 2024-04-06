package com.jen.library.service;

import org.springframework.http.ResponseEntity;
import com.jen.library.dto.UserRequestDTO;
import com.jen.library.model.User;

public interface UserService {
    public ResponseEntity<Object> createUser(UserRequestDTO userRequest);

    public User getUserByEmail(String email);
}
