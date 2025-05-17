package com.example.hotelbooking.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Room {
    @Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

    private String roomNumber;
    private String roomType;
    private double price;
    private boolean available = true;

    private String imageUrl; // ✅ new field for image
    private String imageName;
}
