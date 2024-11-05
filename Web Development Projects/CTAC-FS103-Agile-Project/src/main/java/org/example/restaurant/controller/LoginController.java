package org.example.restaurant.controller;

/*
Handles login logic.
 */

import org.example.restaurant.gui.MainFrame;
import org.example.restaurant.service.UserService;
import org.example.restaurant.gui.LoginFrame;
import org.example.restaurant.util.HashUtil;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController {
    private UserService userService;
    private LoginFrame loginFrame;

    public LoginController(UserService userService, LoginFrame loginFrame) {
        this.userService = userService;
        this.loginFrame = loginFrame;

        // TODO: Set up action listeners for login frame
        LoginFrame.loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Login logic
                String username = loginFrame.usernameField.getText();
                String password = new String(loginFrame.passwordField.getPassword());
                if(userService.login(username, password)) {
                    loginFrame.dispose();
                    new MainFrame(username);
                }
                else{
                    JOptionPane.showMessageDialog(null, "Incorrect Username/Password", "alert", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    // TODO: Implement methods to handle login logic
}
