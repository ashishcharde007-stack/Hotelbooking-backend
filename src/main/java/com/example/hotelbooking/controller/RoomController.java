package com.example.hotelbooking.controller;

import com.example.hotelbooking.entity.Room;
import com.example.hotelbooking.service.RoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.net.MalformedURLException;
import org.springframework.http.MediaType;import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.net.MalformedURLException;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.*;
@RestController
@RequestMapping("/api/rooms")
@CrossOrigin(origins = "*")
public class RoomController {
    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping({ "", "/" })
    public List<Room> getAllRooms() {
        System.out.println("getAllRooms " );


        return roomService.getAllRooms();
    }

    @PostMapping("/post")
    @PreAuthorize("hasRole('ADMIN')")

    public Room createRoom(@RequestBody Room room) {

        return roomService.createRoom(room);
    }

    @GetMapping("/{id}")

    public Room getRoomById(@PathVariable Long id) {
        System.out.println("Authorities from token: " );

        return roomService.getRoomById(id);
    }

    @PutMapping("/{id}")
    public Room updateRoom(@PathVariable Long id, @RequestBody Room room) {
        return roomService.updateRoom(id, room);
    }

    @DeleteMapping("/{id}")
    public void deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Room>> filterRoomsByPrice(
            @RequestParam double minPrice,
            @RequestParam double maxPrice
    ) {
        List<Room> filteredRooms = roomService.filterByPriceRange(minPrice, maxPrice);
        return ResponseEntity.ok(filteredRooms);
    }


}
