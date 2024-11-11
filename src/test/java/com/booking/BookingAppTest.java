package com.booking;

import com.booking.facade.BookingFacade;
import com.booking.model.Event;
import com.booking.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mockStatic;

@ActiveProfiles("test")
@SpringBootTest(properties = "spring.config.name=application-test")
@Transactional
@EnableJms
class BookingAppTest {
    @Autowired
    private BookingFacade bookingFacade;
    @Autowired
    private JmsTemplate jmsTemplate;

    @BeforeAll
    public static void setUp() {
        System.setProperty("org.apache.activemq.SERIALIZABLE_PACKAGES", "java.lang,javax.security,java.util,org.apache.activemq,org.fusesource.hawtbuf,com.thoughtworks.xstream.mapper,com.booking.model");
    }

    @Test
    void testMainRunsApplication() {
        // Arrange
        try (var mockedSpringApplication = mockStatic(SpringApplication.class)) {
            mockedSpringApplication.when(() -> SpringApplication.run(BookingApp.class, new String[]{})).thenReturn(null);

            // Act
            BookingApp.main(new String[]{});

            // Assert
            mockedSpringApplication.verify(() -> SpringApplication.run(BookingApp.class, new String[]{}));
        }
    }

    @Test
    void testCreateEventReturnsCorrectTitle() {
        // Arrange
        Event event = new Event("demo", new Date(), 50d);

        // Act
        Event createdEvent = bookingFacade.createEvent(event);

        // Assert
        assertEquals("demo", createdEvent.getTitle());
    }

    @Test
    void testCreateUserReturnsCorrectName() {
        // Arrange
        User user = new User("test", "test@test.com");

        // Act
        User createdUser = bookingFacade.createUser(user);

        // Assert
        assertEquals("test", createdUser.getName());
    }

    @Test
    void testCreateUserAndRetrieveByIdReturnsCorrectEmail() {
        // Arrange
        User user = new User("test", "test@test.com");
        User savedUser = bookingFacade.createUser(user);

        // Act
        User retrievedUser = bookingFacade.getUserById(savedUser.getId());

        // Assert
        assertEquals("test@test.com", retrievedUser.getEmail());
    }
}
