package com.backend.backend.Service;

import com.backend.backend.dto.LoginDTO;
import com.backend.backend.dto.RestPasswordDTO;
import com.backend.backend.dto.UserDTO;

public interface AuthService {
    boolean register(UserDTO userDTO);

    String login(LoginDTO loginDTO);

    void logout(String token);

    boolean resetPassword(RestPasswordDTO restPasswordDTO);
}
