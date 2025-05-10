package com.backend.backend.dto;

import lombok.Data;

@Data
public class LoginResponse {
    private String token;
    private String userId;
    private String email;

    public LoginResponse(String token , String userId , String email) {
        this.token = token;
        this.userId = userId;
        this.email  = email;
    }
}
