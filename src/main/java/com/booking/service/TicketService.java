package com.booking.service;

import com.booking.dao.TicketDao;
import com.booking.model.Event;
import com.booking.model.Ticket;
import com.booking.model.User;
import com.booking.model.UserAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class TicketService {

    private static final Logger logger = LoggerFactory.getLogger(TicketService.class);

    @Autowired
    private TicketDao ticketDao;
    @Autowired
    private EventService eventService;
    @Autowired
    private UserAccountService userAccountService;

    @Transactional
    public Ticket bookTicket(long userId, long eventId, int place, int category) {
        logger.info("booking ticket userId: {}, eventId: {}", userId, eventId);
        return ticketDao.save(new Ticket(new Date().getTime(), eventId, userId, category, place));
    }

    public List<Ticket> getBookedTickets(long userId, int pageSize, int pageNum) {
        logger.info("get booked tickets userId: {}", userId);
        return ticketDao.findByUserId(userId);
    }

    public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) {
        logger.info("get booked tickets eventId: {}", event.getId());
        return ticketDao.findByEventId(event.getId());
    }

    public boolean cancelTicket(long id) {
        logger.info("cancel ticket id: {}", id);
        ticketDao.deleteById(id);
        return true;
    }

    public List<Ticket> getAllTickets() {
        return ticketDao.findAll();
    }
}
