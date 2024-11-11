package com.booking;

import com.booking.dao.EventDao;
import com.booking.dao.TicketDao;
import com.booking.dao.UserAccountDao;
import com.booking.dao.UserDao;
import com.booking.model.Event;
import com.booking.model.Ticket;
import com.booking.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(properties = "spring.config.name=application-test")
@EnableJms
public class BookingListenerIntegrationTest {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserAccountDao accountDao;

    @Autowired
    private EventDao eventDao;

    @Autowired
    private TicketDao ticketDao;

    @Value("${jms.message}")
    private String bookingMessage;

    private User userSaved;
    private Event eventSaved;
    private Ticket ticket;

    @BeforeEach
    public void setup() {
        // Arrange
        ticketDao.deleteAll();
        eventDao.deleteAll();
        userDao.deleteAll();

        User user = new User();
        user.setName("Test");
        user.setEmail("test@test.com");

        Event event = new Event();
        event.setTitle("Test");
        event.setDate(new Date());
        event.setTicketPrice(20.0);

        userSaved = userDao.save(user);
        eventSaved = eventDao.save(event);

        ticket = new Ticket();
        ticket.setUserId(userSaved.getId());
        ticket.setEventId(eventSaved.getId());
        ticket.setPlace(10);
        ticket.setCategory(1);

        jmsTemplate.convertAndSend(bookingMessage, ticket);
    }

    private List<Ticket> getFilteredTickets() throws InterruptedException {
        // Espera para simular la recepción del mensaje en la cola
        Thread.sleep(500);
        List<Ticket> all = ticketDao.findAll();
        return all.stream()
                .filter(t -> t.getUserId() == userSaved.getId() && t.getEventId() == eventSaved.getId())
                .toList();
    }

    @Test
    public void testTicketBookingCreatesOneTicket() throws InterruptedException {
        // Act
        List<Ticket> filteredTickets = getFilteredTickets();

        // Assert
        assertEquals(1, filteredTickets.size());
    }

    @Test
    public void testTicketIsPresentAfterBooking() throws InterruptedException {
        // Act
        List<Ticket> filteredTickets = getFilteredTickets();

        // Assert
        assertTrue(filteredTickets.stream().findFirst().isPresent());
    }

    @Test
    public void testTicketUserIdMatches() throws InterruptedException {
        // Act
        Ticket bookedTicket = getFilteredTickets().stream().findFirst().orElseThrow();

        // Assert
        assertEquals(ticket.getUserId(), bookedTicket.getUserId());
    }

    @Test
    public void testTicketEventIdMatches() throws InterruptedException {
        // Act
        Ticket bookedTicket = getFilteredTickets().stream().findFirst().orElseThrow();

        // Assert
        assertEquals(ticket.getEventId(), bookedTicket.getEventId());
    }

    @Test
    public void testTicketCategoryMatches() throws InterruptedException {
        // Act
        Ticket bookedTicket = getFilteredTickets().stream().findFirst().orElseThrow();

        // Assert
        assertEquals(ticket.getCategory(), bookedTicket.getCategory());
    }

    @Test
    public void testTicketPlaceMatches() throws InterruptedException {
        // Act
        Ticket bookedTicket = getFilteredTickets().stream().findFirst().orElseThrow();

        // Assert
        assertEquals(ticket.getPlace(), bookedTicket.getPlace());
    }
}
