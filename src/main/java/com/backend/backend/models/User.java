package com.backend.backend.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "users")
public class User {
    @Id
    public String id;
    public String name;
    public String email;
    public String password;
    public String role;

}
