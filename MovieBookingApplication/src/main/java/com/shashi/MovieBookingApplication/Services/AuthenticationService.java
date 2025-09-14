package com.shashi.MovieBookingApplication.Services;

import com.shashi.MovieBookingApplication.DTOs.LoginRequestDTO;
import com.shashi.MovieBookingApplication.DTOs.LoginResponseDTO;
import com.shashi.MovieBookingApplication.DTOs.RegisterRequestDTO;
import com.shashi.MovieBookingApplication.Entities.User;
import com.shashi.MovieBookingApplication.Repositories.UserRepository;
import com.shashi.MovieBookingApplication.jwt.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthenticationService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTService jwtService;


    public User registerNormalUser(RegisterRequestDTO registerRequestDTO) {
        // Check if the user is already registered
        if (userRepository.findByUsername(registerRequestDTO.getUsername()).isPresent()) {
            throw new RuntimeException("User already registered");
        }

        // Set the role
        Set<String> roles = new HashSet<String>();
        roles.add("ROLE_USER");

        // Create a new User
        User user = new User();

        // Set the User properties
        user.setUsername(registerRequestDTO.getUsername());
        user.setEmail(registerRequestDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));
        user.setRoles(roles);

        // Save the new User
        return userRepository.save(user);
    }

    public User registerAdminUser(RegisterRequestDTO registerRequestDTO) {
        // Check if the user is already registered
        if (userRepository.findByUsername(registerRequestDTO.getUsername()).isPresent()) {
            throw new RuntimeException("User already registered");
        }

        // Set the role
        Set<String> roles = new HashSet<String>();

        // Admin should have both the roles
        roles.add("ROLE_ADMIN");
        roles.add("ROLE_USER");

        // Create a new User
        User user = new User();

        // Set the User properties
        user.setUsername(registerRequestDTO.getUsername());
        user.setEmail(registerRequestDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));
        user.setRoles(roles);

        // Save the new User
        return userRepository.save(user);
    }

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        User user = userRepository.findByUsername(loginRequestDTO.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDTO.getUsername(),
                        loginRequestDTO.getPassword()
                )
        );

        String token = jwtService.generateToken(user);

        return LoginResponseDTO.builder()
                .jwtToken(token)
                .username(user.getUsername())
                .roles(user.getRoles())
                .build();
    }
}
