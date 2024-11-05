package com.booking;

import com.booking.dao.EventDao;
import com.booking.dao.TicketDao;
import com.booking.dao.UserAccountDao;
import com.booking.dao.UserDao;
import com.booking.model.Event;
import com.booking.model.Ticket;
import com.booking.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;

import java.util.Date;
import java.util.List;

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

    @Test
    public void testBookTicket() throws InterruptedException {

        ticketDao.deleteAll();
        var user = new User();
        user.setName("Test");
        user.setEmail("test@test.com");

        var event = new Event();
        event.setTitle("Test");
        event.setDate(new Date());
        event.setTicketPrice(20.0);

        User userSaved = userDao.save(user);
        Event eventSaved = eventDao.save(event);

        var ticket = new Ticket();
        ticket.setUserId(user.getId());
        ticket.setEventId(event.getId());
        ticket.setPlace(10);
        ticket.setCategory(1);

        jmsTemplate.convertAndSend(bookingMessage, ticket);

        Thread.sleep(500);

        List<Ticket> all = ticketDao.findAll();


        var filteredTickets = all.stream()
                .filter(t -> t.getUserId() == userSaved.getId() && t.getEventId() == eventSaved.getId())
                .toList();

        Assertions.assertEquals(1, filteredTickets.size());
        Assertions.assertTrue(filteredTickets.stream().findFirst().isPresent());

        var bookedTicket = filteredTickets.stream().findFirst().get();

        Assertions.assertEquals(ticket.getUserId(), bookedTicket.getUserId());
        Assertions.assertEquals(ticket.getEventId(), bookedTicket.getEventId());
        Assertions.assertEquals(ticket.getCategory(), bookedTicket.getCategory());
        Assertions.assertEquals(ticket.getPlace(), bookedTicket.getPlace());
    }
}
