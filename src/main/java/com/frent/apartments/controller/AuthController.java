package com.frent.apartments.controller;

import com.frent.apartments.dto.AuthenticationRequest;
import com.frent.apartments.dto.AuthenticationResponse;
import com.frent.apartments.dto.ChangePasswordRequest;
import com.frent.apartments.dto.RegisterRequest;
import com.frent.apartments.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping("/change-password")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> changePassword(
            @RequestBody ChangePasswordRequest request,
            Principal principal
    ) {
        authenticationService.changePassword(principal.getName(), request);
        return ResponseEntity.ok("Password changed successfully");
    }
}