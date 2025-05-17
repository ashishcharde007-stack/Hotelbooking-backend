
package com.example.hotelbooking.service;

import com.example.hotelbooking.dto.RegisterRequest;
import com.example.hotelbooking.entity.Role;
import com.example.hotelbooking.entity.User;
import com.example.hotelbooking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired private UserRepository userRepo;
    @Autowired private PasswordEncoder encoder;

    public void register(RegisterRequest req) {
        User user = new User();
        user.setUsername(req.username);
        user.setEmail(req.email);
        user.setPassword(encoder.encode(req.password));
        user.setRole(Role.USER);
        userRepo.save(user);
    }
}
