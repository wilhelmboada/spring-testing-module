package com.booking;

import com.booking.facade.BookingFacade;
import com.booking.model.Event;
import com.booking.model.User;
import com.github.javafaker.Faker;
import jakarta.xml.bind.JAXBException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

@Component
public class StartupRunner implements CommandLineRunner {
    private static final Faker faker = new Faker();

    private final BookingFacade bookingFacade;

    public StartupRunner(BookingFacade bookingFacade) {
        this.bookingFacade = bookingFacade;
    }

    @Override
    public void run(String... args) throws JAXBException, IOException {
        System.out.println("Running startup logic...");

        for (int i = 0; i < 30; i++) {
            bookingFacade.createEvent(new Event(faker.book().title(), new Date(), 50d));
        }

        for (int i = 0; i < 50; i++) {
            bookingFacade.createUser(new User(faker.name().name(), faker.name().username() + "@test.com"));
        }

        bookingFacade.preloadTickets("src/main/resources/tickets.xml");

        System.out.println("Tickets preloaded successfully.");
    }
}
