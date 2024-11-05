package org.example.restaurant.gui;

import org.example.restaurant.model.MenuItem;
import org.example.restaurant.service.InventoryService;
import org.example.restaurant.service.MenuService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class MenuManagementPanel extends JPanel {
    private JTextField itemNameField;
    private JTextArea itemDescriptionArea;
    private JTextField itemPriceField;
    private JTextField itemIngredientsField;
    private JButton addButton;
    private JButton removeButton;
    private JButton editButton;
    private JButton viewButton;
    private JTextArea menuDisplayArea;

    private MenuService menuService;

    public MenuManagementPanel(MenuService menuService) {
        this.menuService = menuService;
        InventoryService inv = new InventoryService();

        // Initialize components
        itemNameField = new JTextField(20);
        itemDescriptionArea = new JTextArea(3, 20);
        itemPriceField = new JTextField(10);
        itemIngredientsField = new JTextField(30);
        addButton = new JButton("Add");
        removeButton = new JButton("Remove");
        editButton = new JButton("Edit");
        viewButton = new JButton("View");
        menuDisplayArea = new JTextArea(10, 30);
        menuDisplayArea.setEditable(false);

        // Add components to panel
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Item Name:"), gbc);

        gbc.gridx = 1;
        add(itemNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Description:"), gbc);

        gbc.gridx = 1;
        add(new JScrollPane(itemDescriptionArea), gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Price:"), gbc);

        gbc.gridx = 1;
        add(itemPriceField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Ingredients (separated by ;):"), gbc);

        gbc.gridx = 1;
        add(itemIngredientsField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        add(addButton, gbc);

        gbc.gridy = 5;
        add(removeButton, gbc);

        gbc.gridy = 6;
        add(editButton, gbc);

        gbc.gridy = 7;
        add(viewButton, gbc);

        gbc.gridy = 8;
        add(new JScrollPane(menuDisplayArea), gbc);

        // Set up action listeners for buttons
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = itemNameField.getText();
                String description = itemDescriptionArea.getText();
                double price;
                try {
                    price = Double.parseDouble(itemPriceField.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(MenuManagementPanel.this, "Invalid price.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String[] ingredientsArray = itemIngredientsField.getText().split(";");
                MenuItem newItem = new MenuItem(name, description, price, Arrays.asList(ingredientsArray));
                menuService.addMenuItem(newItem);
                inv.addIngredientsToInventory(Arrays.asList(ingredientsArray));
                JOptionPane.showMessageDialog(MenuManagementPanel.this, "Menu item added successfully.");
                clearFields();
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = itemNameField.getText();
                menuService.removeMenuItem(name);
                JOptionPane.showMessageDialog(MenuManagementPanel.this, "Menu item removed successfully.");
                clearFields();
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = itemNameField.getText();
                String description = itemDescriptionArea.getText();
                double price;
                try {
                    price = Double.parseDouble(itemPriceField.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(MenuManagementPanel.this, "Invalid price.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String[] ingredientsArray = itemIngredientsField.getText().split(";");
                MenuItem updatedItem = new MenuItem(name, description, price, Arrays.asList(ingredientsArray));
                menuService.editMenuItem(updatedItem);
                inv.addIngredientsToInventory(Arrays.asList(ingredientsArray));
                JOptionPane.showMessageDialog(MenuManagementPanel.this, "Menu item edited successfully.");
                clearFields();
            }
        });

        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuDisplayArea.setText("");
                for (MenuItem item : menuService.getAllMenuItems()) {
                    menuDisplayArea.append("Name: " + item.getName() + "\n");
                    menuDisplayArea.append("Description: " + item.getDescription() + "\n");
                    menuDisplayArea.append("Price: $" + item.getPrice() + "\n");
                    menuDisplayArea.append("Ingredients: " + String.join(", ", item.getIngredients()) + "\n\n");
                }
            }
        });
    }

    private void clearFields() {
        itemNameField.setText("");
        itemDescriptionArea.setText("");
        itemPriceField.setText("");
        itemIngredientsField.setText("");
    }

    public JButton getAddButton() {
        return addButton;
    }

    public JButton getRemoveButton() {
        return removeButton;
    }

    public JButton getEditButton() {
        return editButton;
    }

    public JButton getViewButton() {
        return viewButton;
    }

    public JTextField getNameField() {
        return itemNameField;
    }

    public JTextArea getDescriptionField() {
        return itemDescriptionArea;
    }

    public JTextField getPriceField() {
        return itemPriceField;
    }

    public JTextField getIngredientsField() {
        return itemIngredientsField;
    }

    public JTextArea getMenuDisplayArea() {
        return menuDisplayArea;
    }
}

