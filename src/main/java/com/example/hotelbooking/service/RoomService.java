package com.example.hotelbooking.service;

import com.example.hotelbooking.entity.Room;
import com.example.hotelbooking.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {
    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<Room> getAllRooms() {
        System.out.println("Authorities from token: ");
        return roomRepository.findAll();
    }

    public Room createRoom(Room room) {
        return roomRepository.save(room);
    }

    public Room getRoomById(Long id) {
        return roomRepository.findById(id).orElse(null);
    }

    public void deleteRoom(Long id) {
        roomRepository.deleteById(id);
    }

    public Room updateRoom(Long id, Room updatedRoom) {
        Room existing = roomRepository.findById(id).orElse(null);
        if (existing != null) {
            existing.setRoomNumber(updatedRoom.getRoomNumber());
            existing.setRoomType(updatedRoom.getRoomType());
            existing.setPrice(updatedRoom.getPrice());
            existing.setAvailable(updatedRoom.isAvailable());
            existing.setImageUrl(updatedRoom.getImageUrl());
            return roomRepository.save(existing);
        }
        return null;
    }
    public List<Room> filterByPriceRange(double minPrice, double maxPrice) {
        return roomRepository.findByPriceBetween(minPrice, maxPrice);
    }



}
