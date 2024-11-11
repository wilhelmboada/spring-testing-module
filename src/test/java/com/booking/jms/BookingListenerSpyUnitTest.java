package com.booking.jms;

import com.booking.facade.BookingFacade;
import com.booking.model.Ticket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class BookingListenerSpyUnitTest {
    @Spy
    private BookingFacade bookingFacade;

    @InjectMocks
    private BookingListener bookingListener;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldBookTicketWhenBookingIsSuccessful() {
        // Arrange
        var ticket = new Ticket();
        ticket.setUserId(1);
        ticket.setEventId(1);
        ticket.setPlace(10);
        ticket.setCategory(1);

        doReturn(ticket).when(bookingFacade).bookTicket(
                ticket.getUserId(),
                ticket.getEventId(),
                ticket.getPlace(),
                ticket.getCategory()
        );

        // Act
        bookingListener.bookTicket(ticket);

        // Assert
        verify(bookingFacade, times(1)).bookTicket(
                ticket.getUserId(),
                ticket.getEventId(),
                ticket.getPlace(),
                ticket.getCategory()
        );
    }

    @Test
    public void shouldThrowExceptionWhenBookingFails() {
        // Arrange
        var ticket = new Ticket();
        ticket.setUserId(1);
        ticket.setEventId(1);
        ticket.setPlace(10);
        ticket.setCategory(1);

        doThrow(new IllegalArgumentException("Error")).when(bookingFacade).bookTicket(
                ticket.getUserId(),
                ticket.getEventId(),
                ticket.getPlace(),
                ticket.getCategory()
        );

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> bookingListener.bookTicket(ticket));
    }
}
