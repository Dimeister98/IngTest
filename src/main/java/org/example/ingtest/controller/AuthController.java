package org.example.ingtest.controller;

import org.example.ingtest.dto.LoginRequest;
import org.example.ingtest.dto.LoginResponse;
import org.example.ingtest.exception.TooManyAttemptsException;
import org.example.ingtest.service.JwtService;
import org.example.ingtest.service.LoginAttemptService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final LoginAttemptService loginAttemptService;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtService jwtService,
                          LoginAttemptService loginAttemptService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.loginAttemptService = loginAttemptService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        if (loginAttemptService.isBlocked()) {
            throw new TooManyAttemptsException(loginAttemptService.getBlockDurationSecodns());
        }

        try {
            var authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.username(), request.password())
            );
            loginAttemptService.loginSucceeded();
            var userDetails = (UserDetails) authentication.getPrincipal();
            return ResponseEntity.ok(new LoginResponse(jwtService.generateToken(userDetails)));
        } catch (AuthenticationException ex) {
            loginAttemptService.loginFailed();
            throw ex;
        }

    }
}
