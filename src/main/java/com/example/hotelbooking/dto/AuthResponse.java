
package com.example.hotelbooking.dto;

import com.example.hotelbooking.entity.Role;
import lombok.Data;

@Data
public class AuthResponse {
    public String token;
    public String username;
    public String role;
public String email;
public  Long id;
    public AuthResponse(String token, String username, String role , String email, Role roles, Long id) {
        this.token = token;
        this.username = username;
        this.email = email;
        this.role = role;
        this.id = id;

    }


}
