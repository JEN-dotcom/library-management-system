package com.jen.library.service.serviceImplementation;

import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jen.library.dto.UserRequestDTO;
import com.jen.library.model.Role;
import com.jen.library.model.User;
import com.jen.library.repository.RoleRepository;
import com.jen.library.repository.UserRepository;
import com.jen.library.service.UserService;

import java.util.Collections;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

        @Autowired
        private UserRepository userRepository;

        @Autowired
        RoleRepository roleRepository;

        @Autowired
        PasswordEncoder passwordEncoder;

        @Override
        public ResponseEntity<Object> createUser(UserRequestDTO userRequestDTO) {
                Optional<User> existingUser = userRepository.findByEmail(userRequestDTO.getEmail());
                if (existingUser.isPresent()) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already exists");
                }

                Role adminRole = roleRepository.findByAuthority("ADMIN")
                                .orElseGet(() -> roleRepository.save(new Role("ADMIN")));
                User newUser = User.builder()
                                .name(userRequestDTO.getName())
                                .email(userRequestDTO.getEmail())
                                .password(passwordEncoder.encode(userRequestDTO.getPassword()))
                                .authorities(Collections.singleton(adminRole))
                                .build();
                return ResponseEntity.status(HttpStatus.CREATED).body(userRepository.save(newUser));
        }

        @Override
        public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
                return userRepository.findByEmail(email)
                                .orElseThrow();
        }

        @Override
        public User getUserByEmail(String email) {
                return userRepository.findByEmail(email)
                                .orElseThrow();
        }
}