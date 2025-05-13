package com.backend.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class UserDTO {
    public String name;
    public String email;
    public String password;
    public String verificationToken;
}
