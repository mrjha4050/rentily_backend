package com.backend.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ResetPassword {
    @NotBlank(message = "email is required")
    public String email;

    @NotBlank(message = "New password is required")
    public String newPassword;
}
