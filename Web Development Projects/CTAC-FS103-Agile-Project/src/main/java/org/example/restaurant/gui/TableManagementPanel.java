package org.example.restaurant.gui;

import org.example.restaurant.controller.TableController;
import org.example.restaurant.service.TableService;

import javax.swing.*;
import java.awt.*;

public class TableManagementPanel extends JPanel {
    private JTextField tableIdField;
    private JTextField tableNameField;
    private JTextField occupantsField;
    private JComboBox<String> statusComboBox;
    private JButton saveTableButton;
    private JButton deleteTableButton;
    private JTable table;

    // Declare the services and controllers
    public TableService tableService;
    public TableController tableController;

    public TableManagementPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Add Table ID field
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Table ID:"), gbc);
        tableIdField = new JTextField(15);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(tableIdField, gbc);

        // Add Table Name field
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Table Name:"), gbc);
        tableNameField = new JTextField(15);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(tableNameField, gbc);

        // Add Occupants field
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Number of Occupants:"), gbc);
        occupantsField = new JTextField(15);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(occupantsField, gbc);

        // Add Status ComboBox
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Status:"), gbc);
        String[] statusOptions = {"Available", "Reserved", "Occupied"};
        statusComboBox = new JComboBox<>(statusOptions);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(statusComboBox, gbc);

        // Add Save Table button
        saveTableButton = new JButton("Save Table");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(saveTableButton, gbc);

        // Add Delete Table button
        deleteTableButton = new JButton("Delete Table");
        gbc.gridy = 5;
        add(deleteTableButton, gbc);

        // Add Table Display (JTable)
        table = new JTable();
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        add(new JScrollPane(table), gbc);

        // Initialize services and controllers
        tableService = new TableService();
        tableController = new TableController(tableService, this);
    }

    public JTextField getTableIdField() {
        return tableIdField;
    }

    public JTextField getTableNameField() {
        return tableNameField;
    }

    public JTextField getOccupantsField() {
        return occupantsField;
    }

    public JComboBox<String> getStatusComboBox() {
        return statusComboBox;
    }

    public JButton getSaveTableButton() {
        return saveTableButton;
    }

    public JButton getDeleteTableButton() {
        return deleteTableButton;
    }

    public JTable getTable() {
        return table;
    }

    public void updateTableData(Object[][] data) {
        String[] columnNames = {"Table ID", "Table Name", "Occupants", "Status", "Total Price"};
        table.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
    }



    private void addPanel(JPanel panel, String panelName) {
        add(panel, panelName);
    }
}
