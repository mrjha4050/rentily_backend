package com.backend.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class UserDTO {
    public String name;

    @NotBlank(message = "email is required")
    public String email;

    @NotBlank(message = "Password is required")
    public String password;
}
