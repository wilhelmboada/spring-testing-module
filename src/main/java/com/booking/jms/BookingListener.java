package com.booking.jms;

import com.booking.facade.BookingFacade;
import com.booking.model.Ticket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class BookingListener {
    private static final Logger logger = LoggerFactory.getLogger(BookingListener.class);

    private final BookingFacade bookingFacade;

    public  BookingListener(BookingFacade bookingFacade) {
        this.bookingFacade = bookingFacade;
    }

    @JmsListener(destination = "${jms.message}")
    public void bookTicket(Ticket ticket) {
        logger.info("Booking ticket for event: {} and user: {} in place: {} with category: {}",
                ticket.getEventId(), ticket.getUserId(), ticket.getPlace(), ticket.getCategory());
        try {
            bookingFacade.bookTicket(ticket.getUserId(), ticket.getEventId(), ticket.getPlace(), ticket.getCategory());
        } catch (Exception ex) {
            logger.error(String.format("Error: %s", ex.getMessage()), ex);
            throw ex;
        }
    }
}
