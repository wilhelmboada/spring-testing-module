package com.booking.service;

import com.booking.dao.UserDao;
import com.booking.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUserById() {
        // Arrange
        long userId = 1L;
        User mockUser = new User();
        when(userDao.getById(userId)).thenReturn(mockUser);

        // Act
        User result = userService.getUserById(userId);

        // Assert
        assertNotNull(result);
        verify(userDao).getById(userId);
    }

    @Test
    void testGetUserByIdReturnsExpectedUser() {
        // Arrange
        long userId = 1L;
        User mockUser = new User();
        when(userDao.getById(userId)).thenReturn(mockUser);

        // Act
        User result = userService.getUserById(userId);

        // Assert
        assertEquals(mockUser, result);
        verify(userDao).getById(userId);
    }

    @Test
    void testGetUserByEmail() {
        // Arrange
        String email = "test@example.com";
        User mockUser = new User();
        when(userDao.findByEmail(email)).thenReturn(mockUser);

        // Act
        User result = userService.getUserByEmail(email);

        // Assert
        assertNotNull(result);
        verify(userDao).findByEmail(email);
    }

    @Test
    void testGetUserByEmailReturnsExpectedUser() {
        // Arrange
        String email = "test@example.com";
        User mockUser = new User();
        when(userDao.findByEmail(email)).thenReturn(mockUser);

        // Act
        User result = userService.getUserByEmail(email);

        // Assert
        assertEquals(mockUser, result);
        verify(userDao).findByEmail(email);
    }

    @Test
    void testGetUserByEmailNotFound() {
        // Arrange
        String email = "notfound@example.com";
        when(userDao.findByEmail(email)).thenReturn(null);

        // Act
        User result = userService.getUserByEmail(email);

        // Assert
        assertNull(result);
        verify(userDao).findByEmail(email);
    }

    @Test
    void testGetUsersByName() {
        // Arrange
        String name = "John";
        int pageSize = 10;
        int pageNum = 1;
        List<User> mockUsers = List.of(new User(), new User());
        when(userDao.findByName(name)).thenReturn(mockUsers);

        // Act
        List<User> result = userService.getUsersByName(name, pageSize, pageNum);

        // Assert
        assertNotNull(result);
        verify(userDao).findByName(name);
    }

    @Test
    void testGetUsersByNameReturnsCorrectSize() {
        // Arrange
        String name = "John";
        int pageSize = 10;
        int pageNum = 1;
        List<User> mockUsers = List.of(new User(), new User());
        when(userDao.findByName(name)).thenReturn(mockUsers);

        // Act
        List<User> result = userService.getUsersByName(name, pageSize, pageNum);

        // Assert
        assertEquals(mockUsers.size(), result.size());
        verify(userDao).findByName(name);
    }

    @Test
    void testCreateUser() {
        // Arrange
        User user = new User();
        when(userDao.save(user)).thenReturn(user);

        // Act
        User result = userService.createUser(user);

        // Assert
        assertNotNull(result);
        verify(userDao).save(user);
    }

    @Test
    void testCreateUserReturnsSavedUser() {
        // Arrange
        User user = new User();
        when(userDao.save(user)).thenReturn(user);

        // Act
        User result = userService.createUser(user);

        // Assert
        assertEquals(user, result);
        verify(userDao).save(user);
    }

    @Test
    void testUpdateUser() {
        // Arrange
        User user = new User();
        when(userDao.save(user)).thenReturn(user);

        // Act
        User result = userService.updateUser(user);

        // Assert
        assertNotNull(result);
        verify(userDao).save(user);
    }

    @Test
    void testUpdateUserReturnsUpdatedUser() {
        // Arrange
        User user = new User();
        when(userDao.save(user)).thenReturn(user);

        // Act
        User result = userService.updateUser(user);

        // Assert
        assertEquals(user, result);
        verify(userDao).save(user);
    }

    @Test
    void testDeleteUser() {
        // Arrange
        long userId = 1L;

        // Act
        boolean result = userService.deleteUser(userId);

        // Assert
        assertTrue(result);
        verify(userDao).deleteById(userId);
    }
}
