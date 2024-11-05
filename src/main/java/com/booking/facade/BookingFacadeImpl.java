package com.booking.facade;

import com.booking.facade.dto.TicketDto;
import com.booking.facade.dto.TicketsDto;
import com.booking.model.Event;
import com.booking.model.Ticket;
import com.booking.model.User;
import com.booking.model.UserAccount;
import com.booking.service.EventService;
import com.booking.service.TicketService;
import com.booking.service.UserAccountService;
import com.booking.service.UserService;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.xml.transform.stream.StreamSource;

@Service
public class BookingFacadeImpl implements BookingFacade {

    private static final Logger logger = LoggerFactory.getLogger(BookingFacadeImpl.class);

    private final UserService userService;
    private final EventService eventService;
    private final TicketService ticketService;
    private final UserAccountService userAccountService;

    private final Unmarshaller unmarshaller;
    private final PlatformTransactionManager transactionManager;

    public BookingFacadeImpl(UserService userService, EventService eventService, TicketService ticketService, UserAccountService userAccountService,
                             Unmarshaller unmarshaller, PlatformTransactionManager transactionManager) {
        this.userService = userService;
        this.eventService = eventService;
        this.ticketService = ticketService;
        this.userAccountService = userAccountService;
        this.unmarshaller = unmarshaller;
        this.transactionManager = transactionManager;
    }

    @Override
    public Event getEventById(long id) {
        logger.info("getEventById: {}", id);
        return eventService.getEventById(id);
    }

    @Override
    public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) {
        logger.info("get events by title: {}", title);
        return eventService.getEventsByTitle(title, pageSize, pageNum);
    }

    @Override
    public List<Event> getEventsForDay(Date day, int pageSize, int pageNum) {
        logger.info("get events for day: {}", day);
        return eventService.getEventsForDay(day, pageSize, pageNum);
    }

    @Override
    public Event createEvent(Event event) {
        logger.info("create event: {}", event.getId());
        return eventService.createEvent(event);
    }

    @Override
    public Event updateEvent(Event event) {
        logger.info("update event: {}", event.getId());
        return eventService.updateEvent(event);
    }

    @Override
    public boolean deleteEvent(long eventId) {
        logger.info("delete event: {}", eventId);
        return eventService.deleteEvent(eventId);
    }

    @Override
    public User getUserById(long id) {
        logger.info("get user by id: {}", id);
        return userService.getUserById(id);
    }

    @Override
    public User getUserByEmail(String email) {
        logger.info("get user by email: {}", email);
        return userService.getUserByEmail(email);
    }

    @Override
    public List<User> getUsersByName(String name, int pageSize, int pageNum) {
        logger.info("get user by name: {}", name);
        return userService.getUsersByName(name, pageSize, pageNum);
    }

    @Override
    public User createUser(User user) {
        logger.info("create user: {}", user.getId());
        return userService.createUser(user);
    }

    @Override
    public User updateUser(User user) {
        logger.info("update user: {}", user.getId());
        return userService.updateUser(user);
    }

    @Override
    public boolean deleteUser(long userId) {
        logger.info("delete user: {}", userId);
        return userService.deleteUser(userId);
    }

    @Override
    public Ticket bookTicket(long userId, long eventId, int place, int category) {
        logger.info("book ticket userId: {}, eventId: {}", userId, eventId);
        return ticketService.bookTicket(userId, eventId, place, category);
    }

    @Override
    public List<Ticket> getBookedTickets(long userId, int pageSize, int pageNum) {
        logger.info("get booked tickets: {}", userId);
        return ticketService.getBookedTickets(userId, pageSize, pageNum);
    }

    @Override
    public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) {
        logger.info("get booked tickets: {}", event.getId());
        return ticketService.getBookedTickets(event, pageSize, pageNum);
    }

    @Override
    public boolean cancelTicket(long ticketId) {
        logger.info("cancel ticket: {}", ticketId);
        return ticketService.cancelTicket(ticketId);
    }

    @Override
    public UserAccount createUserAccount(UserAccount userAccount) {
        return userAccountService.createUserAccount(userAccount);
    }

    @Override
    public UserAccount addMoneyToUserAccount(long userAccountId, double money) {
        return userAccountService.addMoneyToUserAccount(userAccountId, money);
    }

    @Override
    public List<Ticket> getAllTickets() {
        return ticketService.getAllTickets();
    }

    @Override
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    public void preloadTickets(String filePath) throws IOException, JAXBException {
        FileInputStream inputStream = new FileInputStream(filePath);
        StreamSource streamSource = new StreamSource(inputStream);

        TicketsDto ticketsDto = (TicketsDto) unmarshaller.unmarshal(streamSource);

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName("PreloadTicketsTransaction");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        TransactionStatus status = transactionManager.getTransaction(def);

        try {
            List<TicketDto> ticketList = ticketsDto.getTickets();
            for (TicketDto ticketDto : ticketList) {
                Ticket ticket = new Ticket();
                ticket.setUserId(ticketDto.getUserId());
                ticket.setEventId(ticketDto.getEventId());
                ticket.setPlace(ticketDto.getPlace());
                ticket.setCategory(ticketDto.getCategory());

                ticketService.bookTicket(ticket.getUserId(), ticket.getEventId(), ticket.getPlace(), ticket.getCategory());  // Saving each ticket to the repository
            }
            transactionManager.commit(status);  // Commit the transaction if everything goes well
        } catch (Exception e) {
            transactionManager.rollback(status);  // Rollback if something goes wrong
            throw e;
        } finally {
            inputStream.close();
        }
    }
}
