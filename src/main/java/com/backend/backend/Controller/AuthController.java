package com.backend.backend.Controller;

import com.backend.backend.Service.UserService;
import com.backend.backend.config.JwtTokenProvider;
import com.backend.backend.dto.LoginRequest;
import com.backend.backend.dto.LoginResponse;
import com.backend.backend.models.User;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;

public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        String token = jwtTokenProvider.generateToken(authentication);
        User user = userService.findByEmail(loginRequest.getEmail());
        return ResponseEntity.ok(new LoginResponse(token , user.getId(), user.getEmail()));
    }
}
