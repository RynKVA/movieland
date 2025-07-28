package com.rynkovoi.web;

import com.rynkovoi.common.request.AuthenticateRequest;
import com.rynkovoi.common.response.AuthenticateResponse;
import com.rynkovoi.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticateResponse> authenticate(@RequestBody AuthenticateRequest request) throws Exception {
        return ResponseEntity.ok(authService.authenticate(request));
    }
}
