package com.user.user.service;

import com.user.user.entity.User;
import com.user.user.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.user.user.repository.UserRepository;
import com.user.user.util.JwtUtil;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public String register(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if(user.getRole() == null){
            user.setRole(Role.USER);
        }
        userRepository.save(user);
        return "User registered successfully";
    }

    public String login(String email, String password){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(password,user.getPassword())){
            throw new RuntimeException("Invalid credentials");
        }
        return jwtUtil.generateToken(user.getId(), user.getEmail(), user.getRole());
    }

}
