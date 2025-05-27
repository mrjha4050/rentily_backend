package com.backend.backend.Controller;


import com.backend.backend.Service.AuthService;
import com.backend.backend.dao.UserRepository;
import com.backend.backend.dto.LoginDTO;
import com.backend.backend.dto.RestPasswordDTO;
import com.backend.backend.dto.UserDTO;
import com.backend.backend.models.User;
import com.backend.backend.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthService authService;
    @Autowired private UserRepository userRepository;
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    public AuthenticationController(AuthService authService) {this.authService = authService; }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) {
        boolean created = authService.register(userDTO);
        if (created) return ResponseEntity.ok("Registration successful");
        return ResponseEntity.badRequest().body("User already exists");
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginDTO loginDTO) {
        String token = authService.login(loginDTO);
        if (token != null) return ResponseEntity.ok(Collections.singletonMap("token", token));
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(@RequestHeader("Authorization") String authHeader) {
        String token =  authHeader.replace("Bearer ", "");
        String email = jwtUtil.extractEmail(token);
        Optional<User> UserOpt = userRepository.findByEmail(email);
        if (UserOpt.isPresent()) {
            User user = UserOpt.get();
            return ResponseEntity.ok(Map.of(
                    "id", user.getId(),
                    "name", user.getName(),
                    "email", user.getEmail(),
                    "role", user.getRole()
            ));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(HttpServletRequest request) {
       String token = request.getHeader("Authorization");
       authService.logout(token);
       return ResponseEntity.ok("Logout successful");
    }

    @PostMapping("/reset-Password")
    public ResponseEntity<?> resetPassword(@RequestBody  RestPasswordDTO restPasswordDTO) {
        boolean succes = authService.resetPassword(restPasswordDTO);
        if (succes) return ResponseEntity.ok("Password reset successful");
        return ResponseEntity.badRequest().body("Invalid credentials");
    }

}
