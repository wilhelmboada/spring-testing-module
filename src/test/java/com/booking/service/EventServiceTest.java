package com.booking.service;

import com.booking.dao.EventDao;
import com.booking.model.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EventServiceTest {

    @Mock
    private EventDao eventDao;

    @InjectMocks
    private EventService eventService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetEventByIdShouldReturnEvent() {
        // Arrange
        long eventId = 1L;
        Event mockEvent = new Event();
        when(eventDao.getById(eventId)).thenReturn(mockEvent);

        // Act
        Event result = eventService.getEventById(eventId);

        // Assert
        assertNotNull(result);
    }

    @Test
    void testGetEventByIdShouldReturnCorrectEvent() {
        // Arrange
        long eventId = 1L;
        Event mockEvent = new Event();
        when(eventDao.getById(eventId)).thenReturn(mockEvent);

        // Act
        Event result = eventService.getEventById(eventId);

        // Assert
        assertEquals(mockEvent, result);
    }

    @Test
    void testGetEventByIdShouldInvokeGetByIdOnce() {
        // Arrange
        long eventId = 1L;
        Event mockEvent = new Event();
        when(eventDao.getById(eventId)).thenReturn(mockEvent);

        // Act
        eventService.getEventById(eventId);

        // Assert
        verify(eventDao, times(1)).getById(eventId);
    }

    @Test
    void testGetEventsByTitleShouldReturnNonNullList() {
        // Arrange
        String title = "Concert";
        int pageSize = 10;
        int pageNum = 1;
        List<Event> mockEvents = List.of(new Event(), new Event());
        when(eventDao.findByTitle(title)).thenReturn(mockEvents);

        // Act
        List<Event> result = eventService.getEventsByTitle(title, pageSize, pageNum);

        // Assert
        assertNotNull(result);
    }

    @Test
    void testGetEventsByTitleShouldReturnCorrectSize() {
        // Arrange
        String title = "Concert";
        int pageSize = 10;
        int pageNum = 1;
        List<Event> mockEvents = List.of(new Event(), new Event());
        when(eventDao.findByTitle(title)).thenReturn(mockEvents);

        // Act
        List<Event> result = eventService.getEventsByTitle(title, pageSize, pageNum);

        // Assert
        assertEquals(mockEvents.size(), result.size());
    }

    @Test
    void testGetEventsByTitleShouldInvokeFindByTitleOnce() {
        // Arrange
        String title = "Concert";
        int pageSize = 10;
        int pageNum = 1;
        when(eventDao.findByTitle(title)).thenReturn(List.of(new Event()));

        // Act
        eventService.getEventsByTitle(title, pageSize, pageNum);

        // Assert
        verify(eventDao, times(1)).findByTitle(title);
    }

    @Test
    void testGetEventsForDayShouldReturnNonNullList() {
        // Arrange
        Date day = new Date();
        int pageSize = 10;
        int pageNum = 1;
        List<Event> mockEvents = List.of(new Event(), new Event());
        when(eventDao.findByDate(day)).thenReturn(mockEvents);

        // Act
        List<Event> result = eventService.getEventsForDay(day, pageSize, pageNum);

        // Assert
        assertNotNull(result);
    }

    @Test
    void testGetEventsForDayShouldReturnCorrectSize() {
        // Arrange
        Date day = new Date();
        int pageSize = 10;
        int pageNum = 1;
        List<Event> mockEvents = List.of(new Event(), new Event());
        when(eventDao.findByDate(day)).thenReturn(mockEvents);

        // Act
        List<Event> result = eventService.getEventsForDay(day, pageSize, pageNum);

        // Assert
        assertEquals(mockEvents.size(), result.size());
    }

    @Test
    void testGetEventsForDayShouldInvokeFindByDateOnce() {
        // Arrange
        Date day = new Date();
        when(eventDao.findByDate(day)).thenReturn(List.of(new Event()));

        // Act
        eventService.getEventsForDay(day, 10, 1);

        // Assert
        verify(eventDao, times(1)).findByDate(day);
    }

    @Test
    void testCreateEventShouldReturnNonNullEvent() {
        // Arrange
        Event event = new Event();
        when(eventDao.save(event)).thenReturn(event);

        // Act
        Event result = eventService.createEvent(event);

        // Assert
        assertNotNull(result);
    }

    @Test
    void testCreateEventShouldReturnCorrectEvent() {
        // Arrange
        Event event = new Event();
        when(eventDao.save(event)).thenReturn(event);

        // Act
        Event result = eventService.createEvent(event);

        // Assert
        assertEquals(event, result);
    }

    @Test
    void testCreateEventShouldInvokeSaveOnce() {
        // Arrange
        Event event = new Event();
        when(eventDao.save(event)).thenReturn(event);

        // Act
        eventService.createEvent(event);

        // Assert
        verify(eventDao, times(1)).save(event);
    }

    @Test
    void testUpdateEventShouldReturnNonNullEvent() {
        // Arrange
        Event event = new Event();
        when(eventDao.save(event)).thenReturn(event);

        // Act
        Event result = eventService.updateEvent(event);

        // Assert
        assertNotNull(result);
    }

    @Test
    void testUpdateEventShouldReturnCorrectEvent() {
        // Arrange
        Event event = new Event();
        when(eventDao.save(event)).thenReturn(event);

        // Act
        Event result = eventService.updateEvent(event);

        // Assert
        assertEquals(event, result);
    }

    @Test
    void testUpdateEventShouldInvokeSaveOnce() {
        // Arrange
        Event event = new Event();
        when(eventDao.save(event)).thenReturn(event);

        // Act
        eventService.updateEvent(event);

        // Assert
        verify(eventDao, times(1)).save(event);
    }

    @Test
    void testDeleteEventShouldReturnTrue() {
        // Arrange
        long eventId = 1L;
        doNothing().when(eventDao).deleteById(eventId);

        // Act
        boolean result = eventService.deleteEvent(eventId);

        // Assert
        assertTrue(result);
    }

    @Test
    void testDeleteEventShouldInvokeDeleteByIdOnce() {
        // Arrange
        long eventId = 1L;
        doNothing().when(eventDao).deleteById(eventId);

        // Act
        eventService.deleteEvent(eventId);

        // Assert
        verify(eventDao, times(1)).deleteById(eventId);
    }
}
