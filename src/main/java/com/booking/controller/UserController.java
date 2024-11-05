package com.booking.controller;

import com.booking.facade.BookingFacade;
import com.booking.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final BookingFacade bookingFacade;

    public UserController(BookingFacade bookingFacade) {
        this.bookingFacade = bookingFacade;
    }

    @PostMapping("/")
    public ResponseEntity<User> createUser(
                             @RequestParam String name,
                             @RequestParam String email) {
        return ResponseEntity.ok(bookingFacade.createUser(new User(name, email)));
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(bookingFacade.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(bookingFacade.getUserById(id));
    }
}
