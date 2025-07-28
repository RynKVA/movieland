package com.rynkovoi.service.impl;

import com.rynkovoi.common.request.AuthenticateRequest;
import com.rynkovoi.common.response.AuthenticateResponse;
import com.rynkovoi.model.User;
import com.rynkovoi.securety.JwtService;
import com.rynkovoi.service.AuthService;
import com.rynkovoi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultAuthService implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtService jwtService;

    @Override
    public AuthenticateResponse authenticate(AuthenticateRequest request) throws Exception {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        User user = userService.findByEmail(request.getEmail());
        String token = jwtService.generateToken(user, user.getRole());
        return AuthenticateResponse.builder()
                .token(token)
                .username(user.getFullName())
                .build();
    }
}
