package com.shashi.MovieBookingApplication.Controllers;

import com.shashi.MovieBookingApplication.DTOs.LoginRequestDTO;
import com.shashi.MovieBookingApplication.DTOs.LoginResponseDTO;
import com.shashi.MovieBookingApplication.DTOs.RegisterRequestDTO;
import com.shashi.MovieBookingApplication.Entities.User;
import com.shashi.MovieBookingApplication.Services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register-normal-user")
    // Register Normal User
    public ResponseEntity<User> registerNormalUser(@RequestBody RegisterRequestDTO registerRequestDTO) {
        return ResponseEntity.ok(authenticationService.registerNormalUser(registerRequestDTO));
    }

    @PostMapping("/login")
    // Login Normal User
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        return ResponseEntity.ok(authenticationService.login(loginRequestDTO));
    }
}
