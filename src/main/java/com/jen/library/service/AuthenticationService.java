package com.jen.library.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import com.jen.library.dto.LoginResponseDTO;

@Service
@Transactional
public class AuthenticationService {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    public LoginResponseDTO loginUser(String email, String password) {

        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password));

            String token = tokenService.generateJwt(auth);
            return new LoginResponseDTO(userService.getUserByEmail(email), token);

        } catch (AuthenticationException e) {
            return new LoginResponseDTO(null, e.getMessage());
        }
    }
}