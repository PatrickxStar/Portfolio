package org.example.restaurant.util;

/*
Utility class for password hashing and verification.
 */

import org.mindrot.jbcrypt.BCrypt;

public class HashUtil {

    public static String hashPassword(String password) {
        // Implement method to hash password using BCrypt
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    public static boolean checkPassword(String password, String hashed) {
        // Implement method to check password against hashed value
        return BCrypt.checkpw(password, hashed);
    }
}
