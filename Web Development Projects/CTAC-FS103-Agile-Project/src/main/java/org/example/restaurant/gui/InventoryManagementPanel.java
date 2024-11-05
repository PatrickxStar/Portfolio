package org.example.restaurant.gui;

/*
Interface for managing inventory.
 */

import javax.swing.*;
import java.awt.*;

public class InventoryManagementPanel extends JPanel {
    // Components like tables, labels, buttons
    private JTable inventoryTable;
    private JButton restockButton;
    private JTextField ingredientNameField;
    private JTextField quantityField;

    public InventoryManagementPanel() {
        // Initialize components
        inventoryTable = new JTable(); // This will be populated with inventory data later
        restockButton = new JButton("Restock");
        ingredientNameField = new JTextField(20);
        quantityField = new JTextField(10);

        // Add components to panel
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Ingredient Name:"), gbc);

        gbc.gridx = 1;
        add(ingredientNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Quantity:"), gbc);

        gbc.gridx = 1;
        add(quantityField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(restockButton, gbc);

        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        add(new JScrollPane(inventoryTable), gbc);

        // TODO: Set up action listeners for buttons
        // Note: The action listeners will be set up in the InventoryController, so nothing is needed here
    }

    // TODO: Implement methods to manage inventory
    public JButton getRestockButton() {
        return restockButton;
    }

    public JTextField getIngredientNameField() {
        return ingredientNameField;
    }

    public JTextField getQuantityField() {
        return quantityField;
    }

    public JTable getInventoryTable() {
        return inventoryTable;
    }

    public void updateInventoryTable(Object[][] data, String[] columnNames) {
        inventoryTable.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
    }
}

