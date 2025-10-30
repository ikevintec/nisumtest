package com.alvarez.kd.nisum.application.service;

import com.alvarez.kd.nisum.application.port.in.UserPort;
import com.alvarez.kd.nisum.application.port.out.UserOperationsPort;
import com.alvarez.kd.nisum.common.UseCase;
import com.alvarez.kd.nisum.domain.User;
import com.alvarez.kd.nisum.domain.exceptions.ApplicationException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Service class that implements the UserPort interface to provide user-related functionality.
 *
 * This class handles user validation and interacts with the UserOperationsPort to perform CRUD operations.
 */
@UseCase
public class UserService implements UserPort {

    private final UserOperationsPort userOperationsPort;
    private final String passwordRegex;

    /**
     * Constructs a new UserService instance.
     *
     * @param userOperationsPort The UserOperationsPort to interact with the user data source.
     * @param passwordRegex      The regular expression for validating user passwords.
     */
    public UserService(UserOperationsPort userOperationsPort, @Value("${password.validation.regex}") String passwordRegex) {
        this.userOperationsPort = userOperationsPort;
        this.passwordRegex = passwordRegex;
    }

    /**
     * Retrieves a list of all users.
     *
     * @return A list of User objects.
     */
    @Override
    public List<User> findAll() {
        return userOperationsPort.findAll();
    }

    /**
     * Creates a new user after validating user data.
     *
     * @param user The User object to be created.
     * @return The created User object.
     * @throws ApplicationException If the email already exists or the password does not meet the criteria.
     */
    @Override
    public User create(User user) {
        validateUser(user);
        return userOperationsPort.save(user);
    }

    /**
     * Disables a user with the specified ID.
     *
     * @param id The ID of the user to be disabled.
     */
    @Override
    public void disable(Long id) {
        userOperationsPort.disable(id);
    }

    /**
     * Validates user data, including email uniqueness and password criteria.
     *
     * @param user The User object to be validated.
     * @throws ApplicationException If the email already exists or the password does not meet the criteria.
     */
    private void validateUser(User user) {
        // email validations
        if (userOperationsPort.existsByEmail(user.getEmail())) {
            throw new ApplicationException(
                    "email_already_exists",
                    String.format("Email %s already exists", user.getEmail()),
                    HttpStatus.PRECONDITION_FAILED
            );
        }
        
        // password validations
        Pattern pattern = Pattern.compile(passwordRegex);
        boolean isValidPassword = pattern.matcher(user.getPassword()).matches();
        if (!isValidPassword) {
            throw new ApplicationException(
                    "password_not_standard",
                    "Password must be at least 8 characters, including one uppercase letter, one digit, and one special character. No spaces allowed.",
                    HttpStatus.PRECONDITION_FAILED
            );
        }
    }
}
