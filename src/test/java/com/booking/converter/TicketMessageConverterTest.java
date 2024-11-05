package com.booking.converter;

import com.booking.model.Ticket;
import jakarta.jms.JMSException;
import jakarta.jms.Session;
import jakarta.jms.TextMessage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.when;

@SpringBootTest(properties = "spring.config.name=application-test")
public class TicketMessageConverterTest {

    @Mock
    private Session session;

    @Mock
    private TextMessage textMessage;

    private final TicketMessageConverter ticketMessageConverter = new TicketMessageConverter();

    @Test
    public void testToMessage() throws JMSException {
        var ticket = new Ticket();
        ticket.setUserId(1);
        ticket.setEventId(1);
        ticket.setPlace(10);
        ticket.setCategory(1);

        var expectedJson = """
                {"id":0,"eventId":1,"userId":1,"category":1,"place":10}""";

        when(session.createTextMessage(expectedJson))
                .thenReturn(textMessage);

        var message = ticketMessageConverter.toMessage(ticket, session);

        Assertions.assertEquals(textMessage, message);
    }

    @Test
    public void testFromMessage() throws JMSException {
        var expectedTicket = new Ticket();
        expectedTicket.setUserId(1);
        expectedTicket.setEventId(1);
        expectedTicket.setPlace(10);
        expectedTicket.setCategory(1);

        var ticketJson = """
                {"id":0,"eventId":1,"userId":1,"category":1,"place":10}""";

        when(textMessage.getText())
                .thenReturn(ticketJson);

        var message = ticketMessageConverter.fromMessage(textMessage);

        Assertions.assertInstanceOf(Ticket.class, message);

        var ticket = (Ticket) message;

        Assertions.assertEquals(expectedTicket.getUserId(), ticket.getUserId());
        Assertions.assertEquals(expectedTicket.getEventId(), ticket.getEventId());
        Assertions.assertEquals(expectedTicket.getCategory(), ticket.getCategory());
        Assertions.assertEquals(expectedTicket.getPlace(), ticket.getPlace());
    }

}
