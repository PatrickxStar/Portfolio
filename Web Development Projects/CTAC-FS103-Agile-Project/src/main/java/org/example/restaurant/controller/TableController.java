package org.example.restaurant.controller;

import org.example.restaurant.service.TableService;
import org.example.restaurant.gui.TableManagementPanel;
import org.example.restaurant.model.Table;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TableController {
    private TableService tableService;
    private TableManagementPanel tableManagementPanel;

    public TableController(TableService tableService, TableManagementPanel tableManagementPanel) {
        this.tableService = tableService;
        this.tableManagementPanel = tableManagementPanel;

        setupActionListeners();
        loadTableData();
    }

    private void setupActionListeners() {
        // Remove existing listeners to avoid adding multiple listeners
        for (ActionListener al : tableManagementPanel.getSaveTableButton().getActionListeners()) {
            tableManagementPanel.getSaveTableButton().removeActionListener(al);
        }

        tableManagementPanel.getSaveTableButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveTable();
            }
        });

        for (ActionListener al : tableManagementPanel.getDeleteTableButton().getActionListeners()) {
            tableManagementPanel.getDeleteTableButton().removeActionListener(al);
        }

        tableManagementPanel.getDeleteTableButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteTable();
            }
        });
    }

    private void loadTableData() {
        List<Table> tables = tableService.getAllTables();
        Object[][] data = new Object[tables.size()][5];

        for (int i = 0; i < tables.size(); i++) {
            Table table = tables.get(i);
            data[i][0] = table.getTableId();
            data[i][1] = table.getTableName();
            data[i][2] = table.getOccupants();
            data[i][3] = table.getStatus();
            data[i][4] = table.getTotalPrice();
        }

        tableManagementPanel.updateTableData(data);
    }


    public void saveTable() {
        try {
            // Validate and parse the Table ID and Number of Occupants
            String tableIdText = tableManagementPanel.getTableIdField().getText().trim();
            String occupantsText = tableManagementPanel.getOccupantsField().getText().trim();

            if (tableIdText.isEmpty() || occupantsText.isEmpty()) {
                JOptionPane.showMessageDialog(tableManagementPanel, "Please fill in all fields.");
                return;  // Exit the method if any field is empty
            }

            System.out.println("Save Table button clicked");

            int tableId = Integer.parseInt(tableIdText);
            int occupants = Integer.parseInt(occupantsText);

            // Retrieve the other fields
            String tableName = tableManagementPanel.getTableNameField().getText().trim();
            String status = tableManagementPanel.getStatusComboBox().getSelectedItem().toString();

            // Create a new Table object and add it using the service
            Table table = new Table(tableId, tableName, occupants, status);
            tableService.addTable(table);

            // Refresh the table display
            loadTableData();

            // Show success message once
            JOptionPane.showMessageDialog(tableManagementPanel, "Table saved successfully.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(tableManagementPanel, "Please enter valid numeric values for Table ID and Occupants.");
        }
    }




    public void deleteTable() {
        int selectedRow = tableManagementPanel.getTable().getSelectedRow();

        if (selectedRow != -1) {
            // Delete based on selected row
            int tableId = (int) tableManagementPanel.getTable().getValueAt(selectedRow, 0);
            tableService.deleteTable(tableId);
            loadTableData();  // Refresh the table display
            JOptionPane.showMessageDialog(tableManagementPanel, "Table deleted successfully.");
        } else {
            try {
                // Delete based on Table ID input
                int tableId = Integer.parseInt(tableManagementPanel.getTableIdField().getText());
                tableService.deleteTable(tableId);
                loadTableData();  // Refresh the table display
                JOptionPane.showMessageDialog(tableManagementPanel, "Table deleted successfully.");
            } catch (NumberFormatException ex) {
                // If Table ID is not provided, try deleting based on Table Name
                String tableName = tableManagementPanel.getTableNameField().getText();
                if (!tableName.isEmpty()) {
                    tableService.deleteTableByName(tableName);
                    loadTableData();  // Refresh the table display
                    JOptionPane.showMessageDialog(tableManagementPanel, "Table deleted successfully.");
                }
            }
        }
    }

}
