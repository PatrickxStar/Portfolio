package org.example.restaurant.service;

/*
Handles user login, authentication, and role management.
 */

import org.example.restaurant.model.User;
import org.example.restaurant.util.FileUtil;
import org.example.restaurant.util.HashUtil;
import org.mindrot.jbcrypt.BCrypt;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserService {
    private static final String USER_CSV_FILE = "src/main/resources/org/example/restaurant/users.csv";
    private List<User> users;

    // Constructor to load users from the CSV file or create a new file if it doesn't exist
    public UserService() {
        users = new ArrayList<>();

        // Check if the CSV file exists
        File file = new File(USER_CSV_FILE);
        if (!file.exists()) {
            System.out.println("Users File Not Found, Creating users file...");
            try {
                // Create the file and initialize with default content
                initializeCSVFileWithDefaultContent();
            } catch (IOException e) {
                System.out.println("Error initializing the CSV file: " + e.getMessage());
                return;  // Early return if file creation fails
            }
        }

        // Load users from the CSV file
        loadUsersFromCSV();
    }

    // Method to load users from the CSV file
    private void loadUsersFromCSV() {
        try {
            List<String[]> userData = FileUtil.readFromCSV(USER_CSV_FILE);
            for (String[] userRecord : userData) {
                if (userRecord.length == 3) {  // Ensure the correct number of columns
                    String username = userRecord[0];
                    String password = userRecord[1];
                    String role = userRecord[2];
                    users.add(new User(username, password, role));
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading from the CSV file: " + e.getMessage());
        }
    }

    // Method to create and initialize the CSV file with default content
    private void initializeCSVFileWithDefaultContent() throws IOException {
        List<String[]> defaultData = new ArrayList<>();

        // Add a header row (optional, depending on your CSV structure)
        defaultData.add(new String[]{"Username", "Password", "Role"});

        // Add a default user (optional)
        defaultData.add(new String[]{"admin", HashUtil.hashPassword("admin123"), "manager"});

        // Write the default data to the CSV file
        FileUtil.writeToCSV(USER_CSV_FILE, defaultData);
    }


    // Method to save all users to the CSV file
    private void saveUsersToCSV() {
        List<String[]> userData = new ArrayList<>();
        for (User user : users) {
            String[] userRecord = {
                    user.getUsername(),
                    user.getpasswordHash(),
                    user.getRole()
            };
            userData.add(userRecord);
        }
        try {
            FileUtil.writeToCSV(USER_CSV_FILE, userData);
        } catch (IOException e) {
            System.out.println("Error writing to the CSV file: " + e.getMessage());
        }
    }


    public boolean login(String username, String password) {
        // Implement login logic (hash password, compare with stored hash)
        User user = findUserByUsername(username);
        // Find user if in database and attempt login
        if(user != null){
            // Check userDB password hash against given password
            return HashUtil.checkPassword(password, user.getpasswordHash());
        }
        return false;
    }


    public void removeUser(String username) {
        // Remove a user by username
        users.remove(findUserByUsername(username));
    }

    // Method to add a new user
    public void addUser(User user) {
        users.add(user);
        saveUsersToCSV();
    }

    // Method to get all users
    public List<User> getAllUsers() {
        return users;
    }

    // Method to find a user by username
    public User findUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public void createUser(){
        try{
            String username = JOptionPane.showInputDialog("Please Input a Username");
            if(!username.isEmpty()) {
                String password = JOptionPane.showInputDialog("Please Input a Password");
                if(!password.isEmpty()) {
                    String password2 = JOptionPane.showInputDialog("Please Confirm Password");
                    if(password.equals(password2)) {
                        int response = JOptionPane.showConfirmDialog(null,"Is this user a manager?");
                        User user;
                        if(response == JOptionPane.CANCEL_OPTION) {
                            JOptionPane.showMessageDialog(null,"User Creation Canceled");
                            return;
                        }
                        if(response == JOptionPane.YES_OPTION) {
                            user = new User(username, HashUtil.hashPassword(password), "manager");
                        }
                        else{
                            user = new User(username, HashUtil.hashPassword(password), "server");
                        }
                        addUser(user);
                    }else{
                        JOptionPane.showMessageDialog(null,"Passwords do not match");
                    }
                }else{
                    JOptionPane.showMessageDialog(null,"Please Input a password");
                }
            }else{
                JOptionPane.showMessageDialog(null,"Please Input a Username");
            }
        }catch (Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
            System.out.println(ex.getMessage());
        }
    }
}
