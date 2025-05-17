package com.example.hotelbooking.repository;

import com.example.hotelbooking.entity.Room;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findByPriceBetween(double minPrice, double maxPrice);

}