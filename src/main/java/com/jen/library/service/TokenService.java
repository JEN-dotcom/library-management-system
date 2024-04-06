package com.jen.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import com.jen.library.model.User;

import org.springframework.security.core.GrantedAuthority;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

@Service
public class TokenService {

    @Autowired
    private JwtEncoder jwtEncoder;

    @Autowired
    private JwtDecoder jwtDecoder;

    @Autowired
    private UserService userService;

    public String generateJwt(Authentication auth) {
        Instant now = Instant.now();
        Instant expirationTime = now.plus(60, ChronoUnit.MINUTES);

        String scope = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(expirationTime)
                .subject(auth.getName())
                .claim("roles", scope)

                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public User extractUserFromToken(String token) {

        token = token.replace("Bearer ", "");

        try {

            Jwt claims = jwtDecoder.decode(token);
            String username = claims.getSubject();

            return userService.getUserByEmail(username);
        }

        catch (JwtException e) {
            System.out.println("error + " + e);
            return null;
        }
    }

}
