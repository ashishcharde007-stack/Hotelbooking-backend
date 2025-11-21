
package com.example.hotelbooking.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users")      // <-- FIX: rename table to avoid SQL Server keyword conflicts
@Data
public class User {
    @Id @GeneratedValue
    private Long id;
    private String username;
    private String password;
    private String email;
    @Enumerated(EnumType.STRING)
    private Role role;

    // Getters and setters
}
