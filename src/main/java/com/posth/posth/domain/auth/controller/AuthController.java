package com.posth.posth.domain.auth.controller;

import com.posth.posth.domain.auth.dto.request.LoginRequest;
import com.posth.posth.domain.auth.dto.response.LoginResponse;
import com.posth.posth.domain.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        var response = authService.login(request);
        return ResponseEntity.ok(response);
    }
}
