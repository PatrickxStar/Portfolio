package org.example.restaurant.controller;

import org.example.restaurant.model.MenuItem;
import org.example.restaurant.service.InventoryService;
import org.example.restaurant.service.MenuService;
import org.example.restaurant.gui.MenuManagementPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MenuController {
    private MenuService menuService;
    private InventoryService inventoryService;
    private MenuManagementPanel menuManagementPanel;
    private InventoryController inventoryController;  // Reference to the InventoryController

    public MenuController(MenuService menuService, InventoryService inventoryService, MenuManagementPanel menuManagementPanel, InventoryController inventoryController) {
        this.menuService = menuService;
        this.inventoryService = inventoryService;
        this.menuManagementPanel = menuManagementPanel;
        this.inventoryController = inventoryController;

        setupActionListeners();
    }

    private void setupActionListeners() {
        menuManagementPanel.getAddButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addMenuItem();
            }
        });

        menuManagementPanel.getViewButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("HALLO :D");
                viewMenuItems();
            }
        });
    }

    private void addMenuItem() {
        String name = menuManagementPanel.getNameField().getText();
        String description = menuManagementPanel.getDescriptionField().getText();
        double price = Double.parseDouble(menuManagementPanel.getPriceField().getText());
        List<String> ingredients = List.of(menuManagementPanel.getIngredientsField().getText().split(";"));

        MenuItem menuItem = new MenuItem(name, description, price, ingredients);

        try {
            // Add the new menu item to the menu
            menuService.addMenuItem(menuItem);
            // TESTING
            System.out.println("Ingredients: " + ingredients);
            // Add the ingredients to the inventory
            inventoryService.addIngredientsToInventory(ingredients);

            // Refresh the inventory table in the InventoryManagementPanel
            inventoryController.updateInventoryTable();

            // Notify the user of success
            JOptionPane.showMessageDialog(menuManagementPanel, "Menu item added successfully!");
        } catch (IllegalArgumentException ex) {
            // Show an error message if there is an issue adding the menu item
            JOptionPane.showMessageDialog(menuManagementPanel, ex.getMessage());
        }
    }

    private void viewMenuItems() {
        List<MenuItem> menuItems = menuService.getAllMenuItems();
        StringBuilder displayText = new StringBuilder();

        for (MenuItem item : menuItems) {
            displayText.append("Name: ").append(item.getName()).append("\n")
                    .append("Description: ").append(item.getDescription()).append("\n")
                    .append("Price: $").append(item.getPrice()).append("\n")
                    .append("Ingredients: ").append(String.join(", ", item.getIngredients())).append("\n\n");
        }

        menuManagementPanel.getMenuDisplayArea().setText(displayText.toString());
    }
}

