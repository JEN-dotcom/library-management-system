package com.jen.library.dto;

import com.jen.library.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginResponseDTO {
    private User user;
    private String jwt;
}
