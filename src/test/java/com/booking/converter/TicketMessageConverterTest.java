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
        // Arrange
        var ticket = new Ticket();
        ticket.setUserId(1);
        ticket.setEventId(1);
        ticket.setPlace(10);
        ticket.setCategory(1);

        var expectedJson = """
                {"id":0,"eventId":1,"userId":1,"category":1,"place":10}""";

        when(session.createTextMessage(expectedJson))
                .thenReturn(textMessage);

        // Act
        var message = ticketMessageConverter.toMessage(ticket, session);

        // Assert
        Assertions.assertEquals(textMessage, message);
    }

    @Test
    public void testFromMessage_UserId() throws JMSException {
        // Arrange
        var expectedTicket = new Ticket();
        expectedTicket.setUserId(1);
        expectedTicket.setEventId(1);
        expectedTicket.setPlace(10);
        expectedTicket.setCategory(1);

        var ticketJson = """
                {"id":0,"eventId":1,"userId":1,"category":1,"place":10}""";

        when(textMessage.getText())
                .thenReturn(ticketJson);

        // Act
        var message = ticketMessageConverter.fromMessage(textMessage);
        var ticket = (Ticket) message;

        // Assert
        Assertions.assertEquals(expectedTicket.getUserId(), ticket.getUserId());
    }

    @Test
    public void testFromMessage_EventId() throws JMSException {
        // Arrange
        var expectedTicket = new Ticket();
        expectedTicket.setUserId(1);
        expectedTicket.setEventId(1);
        expectedTicket.setPlace(10);
        expectedTicket.setCategory(1);

        var ticketJson = """
                {"id":0,"eventId":1,"userId":1,"category":1,"place":10}""";

        when(textMessage.getText())
                .thenReturn(ticketJson);

        // Act
        var message = ticketMessageConverter.fromMessage(textMessage);
        var ticket = (Ticket) message;

        // Assert
        Assertions.assertEquals(expectedTicket.getEventId(), ticket.getEventId());
    }

    @Test
    public void testFromMessage_Place() throws JMSException {
        // Arrange
        var expectedTicket = new Ticket();
        expectedTicket.setUserId(1);
        expectedTicket.setEventId(1);
        expectedTicket.setPlace(10);
        expectedTicket.setCategory(1);

        var ticketJson = """
                {"id":0,"eventId":1,"userId":1,"category":1,"place":10}""";

        when(textMessage.getText())
                .thenReturn(ticketJson);

        // Act
        var message = ticketMessageConverter.fromMessage(textMessage);
        var ticket = (Ticket) message;

        // Assert
        Assertions.assertEquals(expectedTicket.getPlace(), ticket.getPlace());
    }

    @Test
    public void testFromMessage_Category() throws JMSException {
        // Arrange
        var expectedTicket = new Ticket();
        expectedTicket.setUserId(1);
        expectedTicket.setEventId(1);
        expectedTicket.setPlace(10);
        expectedTicket.setCategory(1);

        var ticketJson = """
                {"id":0,"eventId":1,"userId":1,"category":1,"place":10}""";

        when(textMessage.getText())
                .thenReturn(ticketJson);

        // Act
        var message = ticketMessageConverter.fromMessage(textMessage);
        var ticket = (Ticket) message;

        // Assert
        Assertions.assertEquals(expectedTicket.getCategory(), ticket.getCategory());
    }
}
