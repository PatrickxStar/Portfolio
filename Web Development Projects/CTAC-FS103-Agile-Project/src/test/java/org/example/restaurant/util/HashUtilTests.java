package org.example.restaurant.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Hashing Utilities Tests")
public class HashUtilTests {
    private String password;
    private String hashedPassword;

    @BeforeEach
    void setUp() {
        password = "securePassword123!";
        hashedPassword = HashUtil.hashPassword(password);
    }

    @Test
    void testHashPassword() {
        // Check that the hashed password is not null and not equal to the original password
        assertNotNull(hashedPassword);
        assertNotEquals(password, hashedPassword);
    }

    @Test
    void testCheckPasswordSuccess() {
        // Verify that the correct password matches the hash
        boolean result = HashUtil.checkPassword(password, hashedPassword);
        assertTrue(result);
    }

    @Test
    void testCheckPasswordFailure() {
        // Verify that an incorrect password does not match the hash
        boolean result = HashUtil.checkPassword("wrongPassword", hashedPassword);
        assertFalse(result);
    }

    // Commented out till I can fully understand how Mockito's mock function works
    // @Test
    void testCheckPasswordWithMockedHash() {
        // Mock the BCrypt check method
        HashUtil hashUtilMock = Mockito.mock(HashUtil.class);
        String mockHash = "$2a$10$Wz8y5cfPRhBihs8gABvDiuM5aOZzS3vGRJfDchJfB9K8Z5NP7LM7G";

        // Mock the checkPassword method
        Mockito.when(HashUtil.checkPassword(password, mockHash)).thenReturn(true);

        // Verify that the correct password matches the mocked hash
        boolean result = HashUtil.checkPassword(password, mockHash);
        assertTrue(result);
    }
}
