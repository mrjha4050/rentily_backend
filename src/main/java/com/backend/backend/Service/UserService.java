package com.backend.backend.Service;


import com.backend.backend.dao.UserRepository;
import com.backend.backend.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User registerUser(User user){
        if (userRepository.findByName(user.getName()) != null) {
            throw new RuntimeException("Name already exists");
        }
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new RuntimeException("Email already exist");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("STUDENT");
        return userRepository.save(user);
    }

    public User findByName (String name){
        return userRepository.findByName(name);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}
