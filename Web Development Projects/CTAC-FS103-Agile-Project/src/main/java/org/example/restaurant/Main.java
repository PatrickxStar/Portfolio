package org.example.restaurant;

import org.example.restaurant.gui.LoginFrame;
import org.example.restaurant.controller.LoginController;
import org.example.restaurant.service.*;


public class Main {
    public static void main(String[] args) {
        // Initialize services
        UserService userService = new UserService();

        LoginFrame loginFrame = new LoginFrame();
        new LoginController(userService, loginFrame);

        loginFrame.setVisible(true);
    }
}
