package com.backend.backend.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;


@Data
public class UserDTO {
    private String name;
    private String email;
    private String password;
    private String role;
    private String phoneNumber;
}
