package com.booking.controller;

import com.booking.facade.BookingFacade;

import java.util.List;

import com.booking.model.Ticket;
import com.booking.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TicketController {

    @Autowired
    private BookingFacade bookingFacade;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Value("${jms.message}")
    private String bookingMessage;

    @GetMapping("/tickets/view")
    public ResponseEntity<List<Ticket>> viewTickets(@RequestParam(defaultValue = "0") int pageNum,
                                                    @RequestParam(defaultValue = "30") int pageSize,
                                                    @RequestParam() Long userId) {
        User userById = bookingFacade.getUserById(userId);
        return ResponseEntity.ok(bookingFacade.getBookedTickets(userById.getId(), pageNum, pageSize));
    }

    @GetMapping("/tickets/all")
    public ResponseEntity<List<Ticket>> viewTickets() {
        return ResponseEntity.ok(bookingFacade.getAllTickets());
    }

    @GetMapping("/tickets/preload")
    public ResponseEntity<String> preloadTickets(@RequestParam String filePath) {
        try {
            // Call the method from BookingFacade, passing the file path
            bookingFacade.preloadTickets(filePath);
            return ResponseEntity.ok("Tickets loaded successfully from file: " + filePath);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error loading tickets: " + e.getMessage());
        }
    }

    @PostMapping("/tickets/save")
    public ResponseEntity<String> bookTicketQueue(@RequestBody Ticket ticket) {
        try {
            jmsTemplate.convertAndSend(bookingMessage, ticket);
        } catch (JmsException e) {
            return ResponseEntity.ok("");
        }
        return ResponseEntity.ok("");
    }
}
