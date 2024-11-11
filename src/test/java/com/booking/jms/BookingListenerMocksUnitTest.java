package com.booking.jms;

import com.booking.facade.BookingFacade;
import com.booking.model.Ticket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class BookingListenerMocksUnitTest {

    @Mock
    private BookingFacade bookingFacade;

    @InjectMocks
    private BookingListener bookingListener;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testBookTicket() {
        // Arrange
        var ticket = new Ticket();
        ticket.setUserId(1);
        ticket.setEventId(1);
        ticket.setPlace(10);
        ticket.setCategory(1);

        when(
                bookingFacade.bookTicket(
                        ticket.getUserId(),
                        ticket.getEventId(),
                        ticket.getPlace(),
                        ticket.getCategory()
                )
        ).thenReturn(ticket);

        // Act
        bookingListener.bookTicket(ticket);

        // Assert
        verify(bookingFacade, times(1)).bookTicket(ticket.getUserId(),
                ticket.getEventId(),
                ticket.getPlace(),
                ticket.getCategory());
    }

    @Test
    public void testBookTicketIfFails() {
        // Arrange
        var ticket = new Ticket();
        ticket.setUserId(1);
        ticket.setEventId(1);
        ticket.setPlace(10);
        ticket.setCategory(1);

        when(
                bookingFacade.bookTicket(
                        ticket.getUserId(),
                        ticket.getEventId(),
                        ticket.getPlace(),
                        ticket.getCategory()
                )
        ).thenThrow(new IllegalArgumentException("Error"));

        // Act & Assert
         assertThrows(IllegalArgumentException.class, () -> bookingListener.bookTicket(ticket));
    }
}
