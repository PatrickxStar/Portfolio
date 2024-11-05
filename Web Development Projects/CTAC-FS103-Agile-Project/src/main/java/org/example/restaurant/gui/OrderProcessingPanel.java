package org.example.restaurant.gui;

import javax.swing.*;
import java.awt.*;

public class OrderProcessingPanel extends JPanel {
    private JTable orderTable;
    private JButton markAsPreparingButton;
    private JButton markAsReadyButton;
    private JButton createOrderButton;
    private JButton assignTableButton;
    private JButton deleteOrderButton; // New button for deleting orders
    private JComboBox<Integer> tableDropdown;

    public OrderProcessingPanel() {
        initializeComponents();
        addComponentsToPanel();
    }

    private void initializeComponents() {
        orderTable = new JTable(); // This will be populated with order data
        markAsPreparingButton = new JButton("Mark as Preparing");
        markAsReadyButton = new JButton("Mark as Ready");
        createOrderButton = new JButton("Create Order");
        assignTableButton = new JButton("Assign to Table");
        deleteOrderButton = new JButton("Delete Order"); // Initialize the delete button
        tableDropdown = new JComboBox<>();
    }

    private void addComponentsToPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        add(new JScrollPane(orderTable), gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.weightx = 0.5;
        gbc.weighty = 0;
        add(markAsPreparingButton, gbc);

        gbc.gridx = 1;
        add(markAsReadyButton, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        add(new JLabel("Select Table:"), gbc);

        gbc.gridx = 1;
        add(tableDropdown, gbc);

        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        add(assignTableButton, gbc);

        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        add(createOrderButton, gbc);

        gbc.gridy = 5; // Adjusting the position for the new button
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        add(deleteOrderButton, gbc); // Add the delete button to the panel
    }

    public JButton getMarkAsPreparingButton() {
        return markAsPreparingButton;
    }

    public JButton getMarkAsReadyButton() {
        return markAsReadyButton;
    }

    public JButton getCreateOrderButton() {
        return createOrderButton;
    }

    public JButton getAssignTableButton() {
        return assignTableButton;
    }

    public JButton getDeleteOrderButton() {
        return deleteOrderButton; // Provide access to the delete button
    }

    public JTable getOrderTable() {
        return orderTable;
    }

    public JComboBox<Integer> getTableDropdown() {
        return tableDropdown;
    }

    public void updateOrderTable(Object[][] data, String[] columnNames) {
        orderTable.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
    }

    public void populateTableDropdown(Integer[] tableIds) {
        tableDropdown.setModel(new DefaultComboBoxModel<>(tableIds));
    }
}
