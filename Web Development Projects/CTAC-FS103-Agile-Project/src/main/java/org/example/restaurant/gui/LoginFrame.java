package org.example.restaurant.gui;


/*
Login Interface
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {
    public static AbstractButton loginButton;
    // Components like text fields, labels, and buttons
    public JTextField usernameField;
    public JPasswordField passwordField;
//    private JButton loginButton;
    private JPanel panel;
    private JLabel usernameLabel;
    private JLabel passwordLabel;

    public LoginFrame() {
        // TODO: Initialize components
        // Create a new panel
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        // Create labels and fields
        usernameLabel = new JLabel("Username:");
        passwordLabel = new JLabel("Password:");
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);

        // Create login button
        loginButton = new JButton("Login");

        // Add components to the panel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(usernameLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(passwordField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(loginButton, gbc);
        // TODO: Add components to frame
        add(panel);
        // TODO: Set up action listeners
            // THESE ARE DONE IN LoginController!
        // TODO: Set frame properties (size, layout, etc.)}
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);


        // TODO: Implement methods for user input retrieval and validation
            // This is also done in LoginController
    }
}
