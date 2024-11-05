package com.booking.controller;

import com.booking.facade.BookingFacade;
import com.booking.model.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private BookingFacade bookingFacade;

    @GetMapping("/")
    public ResponseEntity<List<Ticket>> index() {
        return ResponseEntity.ok(bookingFacade.getAllTickets());
    }
}