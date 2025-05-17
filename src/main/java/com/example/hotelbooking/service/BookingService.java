package com.example.hotelbooking.service;

import com.example.hotelbooking.entity.Booking;
import com.example.hotelbooking.entity.Room;
import com.example.hotelbooking.entity.User;
import com.example.hotelbooking.repository.BookingRepository;
import com.example.hotelbooking.repository.RoomRepository;
import com.example.hotelbooking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
public class BookingService {

    @Autowired
private BookingRepository bookingRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoomRepository roomRepo;

    public Booking createBooking(Long userId, Long roomId, Booking bookingRequest) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Room room = roomRepo.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        bookingRequest.setUser(user);
        bookingRequest.setRoom(room);
        bookingRequest.setStatus("Pending");

        return bookingRepo.save(bookingRequest);
    }
    public List<Booking> getAllBookings() {
        return bookingRepo.findAll();
    }
    public Optional<Booking> getBookingById(Long id) {
        return bookingRepo.findById(id);
    }
    public List<Booking> getBookingsByRoomId(Long roomId) {
        return bookingRepo.findByRoomId(roomId);
    }
    public List<Booking> getBookingsByUserId(Long userId) {
        return bookingRepo.findByUserId(userId);
    }

    public boolean isRoomAvailable(Long roomId, LocalDate checkIn, LocalDate checkOut) {
        List<Booking> bookings = bookingRepo.findByRoomId(roomId);

        for (Booking b : bookings) {
            if (!(checkOut.isBefore(b.getCheckInDate()) || checkIn.isAfter(b.getCheckOutDate()))) {
                return false; // conflict exists
            }
        }

        return true; // room is available
    }
    // ✅ Update booking status (Approve/Reject)
    public Booking updateBookingStatus(Long bookingId, String status) {
        Booking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        booking.setStatus(status);
        return bookingRepo.save(booking);
    }
}
