package com.backend.backend.Service.ServiceImpl;

import com.backend.backend.Service.AuthService;
import com.backend.backend.dao.*;
import com.backend.backend.dto.*;
import com.backend.backend.models.User;
import com.backend.backend.util.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserRepository userRepository, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean register(UserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) return false;
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setName(userDTO.getName());
        user.setRole(userDTO.getRole());
        userRepository.save(user);
        return true;
    }

    @Override
    public String login(LoginDTO loginDTO) {
        Optional<User> userOpt = userRepository.findByEmail(loginDTO.getEmail());
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
                return jwtUtil.generateToken(user.getEmail());
            }
        }
        return null;
    }

    @Override
    public void logout(String token) {

    }

    @Override
    public boolean resetPassword(RestPasswordDTO restPasswordDTO) {
        Optional<User> userOpt = userRepository.findByEmail(restPasswordDTO.getEmail());
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setPassword(passwordEncoder.encode(restPasswordDTO.getNewPassword()));
            userRepository.save(user);
            return true;
        }
        return false;
    }

}
