package com.alvarez.kd.nisum;

import com.alvarez.kd.nisum.application.port.out.UserOperationsPort;
import com.alvarez.kd.nisum.application.service.UserService;
import com.alvarez.kd.nisum.domain.User;
import com.alvarez.kd.nisum.domain.exceptions.ApplicationException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    @Mock
    private UserOperationsPort userOperationsPort;
    
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        userService = new UserService(userOperationsPort, "^(?=.*[A-Z])(?=.*[0-9])(?=.*[@#$%^&+=!]).{8,}$");
    }

    @Test
    public void testFindAll() {
        List<User> userList = new ArrayList<>();
        when(userOperationsPort.findAll()).thenReturn(userList);

        List<User> result = userService.findAll();

        // Verifica que se llame al método findAll en userOperationsPort
        verify(userOperationsPort, times(1)).findAll();

        // Verifica que la lista de usuarios retornada sea la misma que la devuelta por userOperationsPort
        assertEquals(userList, result);
    }

    @Test
    public void testCreateValidUser() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("Password1@");

        when(userOperationsPort.existsByEmail(user.getEmail())).thenReturn(false);
        when(userOperationsPort.save(user)).thenReturn(user);

        User result = userService.create(user);

        // Verifica que se llame al método existsByEmail en userOperationsPort
        verify(userOperationsPort, times(1)).existsByEmail(user.getEmail());

        // Verifica que se llame al método save en userOperationsPort
        verify(userOperationsPort, times(1)).save(user);

        // Verifica que el resultado sea igual al usuario creado
        assertEquals(user, result);
    }

    @Test
    public void testCreateUserWithEmailAlreadyExists() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("Password1@");

        when(userOperationsPort.existsByEmail(user.getEmail())).thenReturn(true);

        try {
            userService.create(user);
            fail("Se esperaba una excepción");
        } catch (ApplicationException e) {
            // Verifica que se llame al método existsByEmail en userOperationsPort
            verify(userOperationsPort, times(1)).existsByEmail(user.getEmail());

            // Verifica el mensaje y el código de estado HTTP de la excepción
            assertEquals("email_already_exists", e.getErrorCode());
            assertEquals("Email test@example.com already exists", e.getMessage());
            assertEquals(HttpStatus.PRECONDITION_FAILED, e.getHttpStatus());
        }
    }

    @Test
    public void testCreateUserWithInvalidPassword() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("invalidpassword");

        when(userOperationsPort.existsByEmail(user.getEmail())).thenReturn(false);

        try {
            userService.create(user);
            fail("Se esperaba una excepción");
        } catch (ApplicationException e) {
            // Verifica que se llame al método existsByEmail en userOperationsPort
            verify(userOperationsPort, times(1)).existsByEmail(user.getEmail());

            // Verifica el mensaje y el código de estado HTTP de la excepción
            assertEquals("password_not_standard", e.getErrorCode());
            assertEquals("Password must be at least 8 characters, including one uppercase letter, one digit, and one special character. No spaces allowed.", e.getMessage());
            assertEquals(HttpStatus.PRECONDITION_FAILED, e.getHttpStatus());
        }
    }

    @Test
    public void testDisableUser() {
        Long userId = 1L;
        userService.disable(userId);

        // Verifica que se llame al método disable en userOperationsPort con el ID correcto
        verify(userOperationsPort, times(1)).disable(userId);
    }
}
