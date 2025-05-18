
package com.example.hotelbooking.controller;

import com.example.hotelbooking.dto.AuthRequest;
import com.example.hotelbooking.dto.AuthResponse;
import com.example.hotelbooking.dto.RegisterRequest;

import com.example.hotelbooking.entity.User;
import com.example.hotelbooking.security.JwtUtil;
import com.example.hotelbooking.service.AuthService;
import com.example.hotelbooking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
//@CrossOrigin(origins = "http://localhost:4200/")
@CrossOrigin(origins = {"http://localhost:4200", "https://hotelbookinsystem-f668f.web.app"})
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired private AuthService authService;
    @Autowired private JwtUtil jwtUtil;
    @Autowired private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        authService.register(request);
        UserDetails user = userService.loadUserByUsername(request.username);
        String token = jwtUtil.generateToken(user.getUsername());
        User users = userService.getUserByUsername(request.username);

        System.out.println(users.getRole());
        return ResponseEntity.ok(new AuthResponse(token, user.getUsername(),user.getAuthorities().toString(),users.getEmail(),users.getRole(), users.getId()));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {


        UserDetails user = userService.loadUserByUsername(request.username);
        if (new BCryptPasswordEncoder().matches(request.password, user.getPassword())) {
            String token = jwtUtil.generateToken(user.getUsername());

            User users = userService.getUserByUsername(request.username);
            System.out.println(users.getRole());
            System.out.println(users.getId());
            return ResponseEntity.ok(new AuthResponse(token, user.getUsername(),user.getAuthorities().toString(),users.getEmail(),users.getRole(),users.getId()));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }
}
