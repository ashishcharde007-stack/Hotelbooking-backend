package com.example.hotelbooking.controller;

import com.example.hotelbooking.entity.Booking;
import com.example.hotelbooking.entity.Room;
import com.example.hotelbooking.repository.BookingRepository;
import com.example.hotelbooking.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;
    @Autowired
    private BookingRepository bookingRepository;

    @PostMapping("/user/{userId}/room/{roomId}")
    public ResponseEntity<Booking> bookRoom(
            @PathVariable Long userId,
            @PathVariable Long roomId,
            @RequestBody Booking booking
    ) {
        Booking createdBooking = bookingService.createBooking(userId, roomId, booking);
        return new ResponseEntity<>(createdBooking, HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long id) {
        return bookingService.getBookingById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Booking>> getBookingsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(bookingService.getBookingsByUserId(userId));
    }

    // Fetch bookings by room
    @GetMapping("/room/{roomId}")
    public ResponseEntity<List<Booking>> getBookingsByRoom(@PathVariable Long roomId) {
        return ResponseEntity.ok(bookingService.getBookingsByRoomId(roomId));
    }

    @GetMapping("/all")

    public ResponseEntity<List<Booking>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    @GetMapping("/availability")
    public ResponseEntity<?> isRoomAvailable(
            @RequestParam Long roomId,
            @RequestParam String checkIn,
            @RequestParam String checkOut
    ) {
        try {
            LocalDate checkInDate = LocalDate.parse(checkIn);
            LocalDate checkOutDate = LocalDate.parse(checkOut);

            boolean available = bookingService.isRoomAvailable(roomId, checkInDate, checkOutDate);
            return ResponseEntity.ok(available);
        } catch (Exception e) {
            // Return a 400 Bad Request if parsing fails
            return ResponseEntity.badRequest().body("Invalid date format. Please use 'YYYY-MM-DD'.");
        }
    }


    @GetMapping("/availability-test")
    public ResponseEntity<String> testParams(
            @RequestParam String checkIn,
            @RequestParam String checkOut
    ) {
        return ResponseEntity.ok("checkIn: " + checkIn + ", checkOut: " + checkOut);
    }


    @PutMapping("/{bookingId}/status")
    public ResponseEntity<?> updateStatus(
            @PathVariable Long bookingId,
            @RequestParam String status) {

        Booking updated = bookingService.updateBookingStatus(bookingId, status);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("deletebooking/{id}")
    public ResponseEntity<?> deleteBooking(@PathVariable Long id) {
        Optional<Booking> bookingOpt =bookingRepository.findById(id);
        if (bookingOpt.isPresent()) {
            bookingRepository.deleteById(id);
            return ResponseEntity.ok("Booking deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Booking not found");
        }
    }
}
