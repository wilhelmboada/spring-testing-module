package com.booking.service;

import com.booking.dao.TicketDao;
import com.booking.model.Event;
import com.booking.model.Ticket;
import com.booking.model.UserAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class TicketServiceTest {

    @Mock
    private TicketDao ticketDao;

    @Mock
    private EventService eventService;

    @Mock
    private UserAccountService userAccountService;

    @InjectMocks
    private TicketService ticketService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testBookTicket_ShouldReturnNotNullTicket() {
        // Arrange
        long userId = 1L;
        long eventId = 2L;
        int place = 5;
        int category = 1;
        double ticketPrice = 50.0;
        double prepaidAmount = 100.0;

        Event event = new Event();
        event.setId(eventId);
        event.setTitle("Concert");
        event.setDate(new Date());
        event.setTicketPrice(ticketPrice);

        UserAccount userAccount = new UserAccount();
        userAccount.setPrepaidAmount(prepaidAmount);

        when(eventService.getEventById(eventId)).thenReturn(event);
        when(userAccountService.getUserAccountByUserId(userId)).thenReturn(userAccount);
        when(ticketDao.save(any(Ticket.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Ticket bookedTicket = ticketService.bookTicket(userId, eventId, place, category);

        // Assert
        assertNotNull(bookedTicket);
    }

    @Test
    void testBookTicket_ShouldSetCorrectEventId() {
        // Arrange
        long userId = 1L;
        long eventId = 2L;
        int place = 5;
        int category = 1;

        Event event = new Event();
        event.setId(eventId);
        when(eventService.getEventById(eventId)).thenReturn(event);
        when(ticketDao.save(any(Ticket.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Ticket bookedTicket = ticketService.bookTicket(userId, eventId, place, category);

        // Assert
        assertEquals(eventId, bookedTicket.getEventId());
    }

    @Test
    void testBookTicket_ShouldSetCorrectUserId() {
        // Arrange
        long userId = 1L;
        long eventId = 2L;
        int place = 5;
        int category = 1;

        when(eventService.getEventById(eventId)).thenReturn(new Event());
        when(ticketDao.save(any(Ticket.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Ticket bookedTicket = ticketService.bookTicket(userId, eventId, place, category);

        // Assert
        assertEquals(userId, bookedTicket.getUserId());
    }

    @Test
    void testBookTicket_ShouldSetCorrectCategory() {
        // Arrange
        long userId = 1L;
        long eventId = 2L;
        int place = 5;
        int category = 1;

        when(eventService.getEventById(eventId)).thenReturn(new Event());
        when(ticketDao.save(any(Ticket.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Ticket bookedTicket = ticketService.bookTicket(userId, eventId, place, category);

        // Assert
        assertEquals(category, bookedTicket.getCategory());
    }

    @Test
    void testBookTicket_ShouldSetCorrectPlace() {
        // Arrange
        long userId = 1L;
        long eventId = 2L;
        int place = 5;
        int category = 1;

        when(eventService.getEventById(eventId)).thenReturn(new Event());
        when(ticketDao.save(any(Ticket.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Ticket bookedTicket = ticketService.bookTicket(userId, eventId, place, category);

        // Assert
        assertEquals(place, bookedTicket.getPlace());
    }

    @Test
    void testBookTicket_ShouldInvokeSaveOnce() {
        // Arrange
        long userId = 1L;
        long eventId = 2L;
        int place = 5;
        int category = 1;

        when(eventService.getEventById(eventId)).thenReturn(new Event());
        when(ticketDao.save(any(Ticket.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        ticketService.bookTicket(userId, eventId, place, category);

        // Assert
        verify(ticketDao, times(1)).save(any(Ticket.class));
    }

    @Test
    void testGetBookedTicketsByUser_ShouldReturnNonNullList() {
        // Arrange
        int pageSize = 10;
        int pageNum = 1;
        List<Ticket> mockTickets = List.of(new Ticket(), new Ticket());

        when(ticketDao.findByUserId(1)).thenReturn(mockTickets);

        // Act
        List<Ticket> result = ticketService.getBookedTickets(1, pageSize, pageNum);

        // Assert
        assertNotNull(result);
    }

    @Test
    void testGetBookedTicketsByUser_ShouldReturnCorrectSize() {
        // Arrange
        int pageSize = 10;
        int pageNum = 1;
        List<Ticket> mockTickets = List.of(new Ticket(), new Ticket());

        when(ticketDao.findByUserId(1)).thenReturn(mockTickets);

        // Act
        List<Ticket> result = ticketService.getBookedTickets(1, pageSize, pageNum);

        // Assert
        assertEquals(mockTickets.size(), result.size());
    }

    @Test
    void testGetBookedTicketsByUser_ShouldInvokeFindByUserIdOnce() {
        // Arrange
        int pageSize = 10;
        int pageNum = 1;

        when(ticketDao.findByUserId(1)).thenReturn(List.of());

        // Act
        ticketService.getBookedTickets(1, pageSize, pageNum);

        // Assert
        verify(ticketDao).findByUserId(1);
    }

    @Test
    void testGetBookedTicketsByEvent_ShouldReturnNonNullList() {
        // Arrange
        Event event = new Event();
        int pageSize = 10;
        int pageNum = 1;
        List<Ticket> mockTickets = List.of(new Ticket(), new Ticket());

        when(ticketDao.findByEventId(event.getId())).thenReturn(mockTickets);

        // Act
        List<Ticket> result = ticketService.getBookedTickets(event, pageSize, pageNum);

        // Assert
        assertNotNull(result);
    }

    @Test
    void testGetBookedTicketsByEvent_ShouldReturnCorrectSize() {
        // Arrange
        Event event = new Event();
        int pageSize = 10;
        int pageNum = 1;
        List<Ticket> mockTickets = List.of(new Ticket(), new Ticket());

        when(ticketDao.findByEventId(event.getId())).thenReturn(mockTickets);

        // Act
        List<Ticket> result = ticketService.getBookedTickets(event, pageSize, pageNum);

        // Assert
        assertEquals(mockTickets.size(), result.size());
    }

    @Test
    void testGetBookedTicketsByEvent_ShouldInvokeFindByEventIdOnce() {
        // Arrange
        Event event = new Event();
        int pageSize = 10;
        int pageNum = 1;

        when(ticketDao.findByEventId(event.getId())).thenReturn(List.of());

        // Act
        ticketService.getBookedTickets(event, pageSize, pageNum);

        // Assert
        verify(ticketDao).findByEventId(event.getId());
    }
}
