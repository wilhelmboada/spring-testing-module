package com.booking.facade;

import com.booking.model.Event;
import com.booking.model.Ticket;
import com.booking.model.User;
import com.booking.service.EventService;
import com.booking.service.TicketService;
import com.booking.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookingFacadeImplTest {

    @Mock
    private UserService userService;

    @Mock
    private EventService eventService;

    @Mock
    private TicketService ticketService;

    @InjectMocks
    private BookingFacadeImpl bookingFacade;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetEventById() {
        // Arrange
        long eventId = 1L;
        Event mockEvent = new Event();
        when(eventService.getEventById(eventId)).thenReturn(mockEvent);

        // Act
        Event result = bookingFacade.getEventById(eventId);

        // Assert
        assertNotNull(result);
    }

    @Test
    void testGetEventsByTitle() {
        // Arrange
        String title = "Concert";
        int pageSize = 10;
        int pageNum = 1;
        List<Event> mockEvents = List.of(new Event(), new Event());
        when(eventService.getEventsByTitle(title, pageSize, pageNum)).thenReturn(mockEvents);

        // Act
        List<Event> result = bookingFacade.getEventsByTitle(title, pageSize, pageNum);

        // Assert
        assertEquals(mockEvents.size(), result.size());
    }

    @Test
    void testGetEventsForDay() {
        // Arrange
        Date day = new Date();
        int pageSize = 10;
        int pageNum = 1;
        List<Event> mockEvents = List.of(new Event(), new Event());
        when(eventService.getEventsForDay(day, pageSize, pageNum)).thenReturn(mockEvents);

        // Act
        List<Event> result = bookingFacade.getEventsForDay(day, pageSize, pageNum);

        // Assert
        assertEquals(mockEvents.size(), result.size());
    }

    @Test
    void testCreateEvent() {
        // Arrange
        Event event = new Event();
        when(eventService.createEvent(event)).thenReturn(event);

        // Act
        Event result = bookingFacade.createEvent(event);

        // Assert
        assertEquals(event, result);
    }

    @Test
    void testUpdateEvent() {
        // Arrange
        Event event = new Event();
        when(eventService.updateEvent(event)).thenReturn(event);

        // Act
        Event result = bookingFacade.updateEvent(event);

        // Assert
        assertEquals(event, result);
    }

    @Test
    void testDeleteEvent() {
        // Arrange
        long eventId = 1L;
        when(eventService.deleteEvent(eventId)).thenReturn(true);

        // Act
        boolean result = bookingFacade.deleteEvent(eventId);

        // Assert
        assertTrue(result);
    }

    @Test
    void testGetUserById() {
        // Arrange
        long userId = 1L;
        User mockUser = new User();
        when(userService.getUserById(userId)).thenReturn(mockUser);

        // Act
        User result = bookingFacade.getUserById(userId);

        // Assert
        assertEquals(mockUser, result);
    }

    @Test
    void testGetUserByEmail() {
        // Arrange
        String email = "test@example.com";
        User mockUser = new User();
        when(userService.getUserByEmail(email)).thenReturn(mockUser);

        // Act
        User result = bookingFacade.getUserByEmail(email);

        // Assert
        assertEquals(mockUser, result);
    }

    @Test
    void testCreateUser() {
        // Arrange
        User user = new User();
        when(userService.createUser(user)).thenReturn(user);

        // Act
        User result = bookingFacade.createUser(user);

        // Assert
        assertEquals(user, result);
    }

    @Test
    void testUpdateUser() {
        // Arrange
        User user = new User();
        when(userService.updateUser(user)).thenReturn(user);

        // Act
        User result = bookingFacade.updateUser(user);

        // Assert
        assertEquals(user, result);
    }

    @Test
    void testDeleteUser() {
        // Arrange
        long userId = 1L;
        when(userService.deleteUser(userId)).thenReturn(true);

        // Act
        boolean result = bookingFacade.deleteUser(userId);

        // Assert
        assertTrue(result);
    }

    @Test
    void testBookTicket() {
        // Arrange
        long userId = 1L;
        long eventId = 100L;
        int place = 5;
        int category = 1;
        Ticket mockTicket = new Ticket();
        when(ticketService.bookTicket(userId, eventId, place, category)).thenReturn(mockTicket);

        // Act
        Ticket result = bookingFacade.bookTicket(userId, eventId, place, category);

        // Assert
        assertEquals(mockTicket, result);
    }

    @Test
    void testGetBookedTicketsByUser() {
        // Arrange
        int pageSize = 10;
        int pageNum = 1;
        List<Ticket> mockTickets = List.of(new Ticket(), new Ticket());
        when(ticketService.getBookedTickets(1, pageSize, pageNum)).thenReturn(mockTickets);

        // Act
        List<Ticket> result = bookingFacade.getBookedTickets(1, pageSize, pageNum);

        // Assert
        assertEquals(mockTickets.size(), result.size());
    }

    @Test
    void testGetBookedTicketsByEvent() {
        // Arrange
        Event event = new Event();
        int pageSize = 10;
        int pageNum = 1;
        List<Ticket> mockTickets = List.of(new Ticket(), new Ticket());
        when(ticketService.getBookedTickets(event, pageSize, pageNum)).thenReturn(mockTickets);

        // Act
        List<Ticket> result = bookingFacade.getBookedTickets(event, pageSize, pageNum);

        // Assert
        assertEquals(mockTickets.size(), result.size());
    }
}
