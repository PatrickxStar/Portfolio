package org.example.restaurant.controller;

import org.example.restaurant.model.InventoryItem;
import org.example.restaurant.service.InventoryService;
import org.example.restaurant.gui.InventoryManagementPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class InventoryController {
    private InventoryService inventoryService;
    private InventoryManagementPanel inventoryManagementPanel;

    public InventoryController(InventoryService inventoryService, InventoryManagementPanel inventoryManagementPanel) {
        this.inventoryService = inventoryService;
        this.inventoryManagementPanel = inventoryManagementPanel;

        setupActionListeners();
        updateInventoryTable(); // Initial population of the table
    }

    private void setupActionListeners() {
        inventoryManagementPanel.getRestockButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restockItem();
            }
        });
    }

    private void restockItem() {
        String ingredientName = inventoryManagementPanel.getIngredientNameField().getText();
        int quantity;
        try {
            quantity = Integer.parseInt(inventoryManagementPanel.getQuantityField().getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(inventoryManagementPanel, "Please enter a valid quantity.");
            return;
        }

        inventoryService.restockItem(ingredientName, quantity);
        updateInventoryTable();
        JOptionPane.showMessageDialog(inventoryManagementPanel, "Inventory updated successfully!");
    }

    public void updateInventoryTable() {
        List<InventoryItem> inventoryItems = inventoryService.getAllItems();
        String[] columnNames = {"Ingredient Name", "Quantity"};
        Object[][] data = inventoryService.getInventoryData();
//        Object[][] data = new Object[inventoryItems.size()][2];
//
//        for (int i = 0; i < inventoryItems.size(); i++) {
//            InventoryItem item = inventoryItems.get(i);
//            data[i][0] = item.getIngredientName();
//            data[i][1] = item.getQuantity();
//        }

        inventoryManagementPanel.updateInventoryTable(data, columnNames);
    }
}

