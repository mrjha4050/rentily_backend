package com.backend.backend.dto;

import lombok.Data;


@Data
public class UserDTO {
    private String name;
    private String email;
    private String password;
    private String verificationToken;
    private String role;
}
