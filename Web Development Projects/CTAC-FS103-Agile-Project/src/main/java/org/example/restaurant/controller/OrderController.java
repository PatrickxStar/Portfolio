package org.example.restaurant.controller;

import org.example.restaurant.model.MenuItem;
import org.example.restaurant.model.Order;
import org.example.restaurant.model.Table;
import org.example.restaurant.service.InventoryService;
import org.example.restaurant.service.OrderService;
import org.example.restaurant.service.TableService;
import org.example.restaurant.gui.OrderProcessingPanel;
import org.example.restaurant.util.FileUtil;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class OrderController {
    private OrderService orderService;
    private TableService tableService;
    private OrderProcessingPanel orderProcessingPanel;

    public OrderController(OrderService orderService, TableService tableService, OrderProcessingPanel orderProcessingPanel) {
        this.orderService = orderService;
        this.tableService = tableService;
        this.orderProcessingPanel = orderProcessingPanel;

        setupActionListeners();
        loadOrderTable();
        populateTableDropdown();
    }

    private void setupActionListeners() {
        orderProcessingPanel.getMarkAsPreparingButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                markOrderAsPreparing();
            }
        });

        orderProcessingPanel.getMarkAsReadyButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                markOrderAsReady();
            }
        });

        orderProcessingPanel.getCreateOrderButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createOrder();
            }
        });

        orderProcessingPanel.getAssignTableButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                assignTableToOrder();
            }
        });

        orderProcessingPanel.getDeleteOrderButton().addActionListener(new ActionListener() { // Add listener for delete button
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteOrder();
            }
        });
    }

    private void markOrderAsPreparing() {
        int selectedRow = orderProcessingPanel.getOrderTable().getSelectedRow();
        if (selectedRow >= 0) {
            int orderId = (Integer) orderProcessingPanel.getOrderTable().getValueAt(selectedRow, 0);
            orderService.updateOrderStatus(orderId, "Preparing");
            loadOrderTable();
            JOptionPane.showMessageDialog(orderProcessingPanel, "Order marked as Preparing.");
        } else {
            JOptionPane.showMessageDialog(orderProcessingPanel, "Please select an order to mark as Preparing.");
        }
    }

    private void markOrderAsReady() {
        int selectedRow = orderProcessingPanel.getOrderTable().getSelectedRow();
        if (selectedRow >= 0) {
            int orderId = (Integer) orderProcessingPanel.getOrderTable().getValueAt(selectedRow, 0);
            orderService.updateOrderStatus(orderId, "Ready");
            loadOrderTable();
            JOptionPane.showMessageDialog(orderProcessingPanel, "Order marked as Ready.");
        } else {
            JOptionPane.showMessageDialog(orderProcessingPanel, "Please select an order to mark as Ready.");
        }
    }

    private void createOrder() {
        // Read menu items from the CSV file
        List<MenuItem> menuItems;
        try {
            FileUtil fileUtil = new FileUtil();
            menuItems = fileUtil.readMenuItemsFromCSV("src/main/resources/org/example/restaurant/menuItems.csv");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(orderProcessingPanel, "Error reading menu items. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Get the order ID from the user
        String orderIdStr = JOptionPane.showInputDialog(orderProcessingPanel, "Enter Order ID:");

        if (orderIdStr != null && !orderIdStr.trim().isEmpty()) {
            int orderId = Integer.parseInt(orderIdStr);

            // Get the selected table ID from the dropdown
            int tableId = (Integer) orderProcessingPanel.getTableDropdown().getSelectedItem();

            // Convert menu items to an array of food choices for the dialog
            String[] foodChoices = menuItems.stream()
                    .map(MenuItem::getName)
                    .toArray(String[]::new);

            // Show the food selection dialog
            String selectedFood = (String) JOptionPane.showInputDialog(
                    orderProcessingPanel,
                    "Select Food Item:",
                    "Food Selection",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    foodChoices,
                    foodChoices[0]
            );

            if (selectedFood != null && !selectedFood.trim().isEmpty()) {
                // Ask for the quantity of the selected food item
                String quantityStr = JOptionPane.showInputDialog(orderProcessingPanel, "Enter Quantity:");
                int quantity = 1; // Default quantity
                try {
                    if (quantityStr != null && !quantityStr.trim().isEmpty()) {
                        quantity = Integer.parseInt(quantityStr);
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(orderProcessingPanel, "Invalid quantity. Defaulting to 1.", "Warning", JOptionPane.WARNING_MESSAGE);
                }

                // Find the selected MenuItem object based on the selected food name
                MenuItem selectedMenuItem = menuItems.stream()
                        .filter(item -> item.getName().equals(selectedFood))
                        .findFirst()
                        .orElse(null);

                if (selectedMenuItem != null) {
                    // Create a new order with the selected table ID and order ID
                    Order order = new Order(orderId, tableId, selectedMenuItem.getPrice());

                    // Update our inventory to reflect that order
                    InventoryService inv = new InventoryService();
                    inv.reduceIngredientsForMenuItem(selectedMenuItem);

                    // Add the selected item to the order with the specified quantity and price
                    order.addItem(selectedMenuItem.getName(), quantity, selectedMenuItem.getPrice());

                    // Save the order using orderService
                    orderService.addOrder(order);

                    // Reload the order table to reflect the new order
                    loadOrderTable();

                    JOptionPane.showMessageDialog(orderProcessingPanel, "Order created successfully.");
                } else {
                    JOptionPane.showMessageDialog(orderProcessingPanel, "Selected food item not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(orderProcessingPanel, "No food item selected.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(orderProcessingPanel, "Order ID cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void assignTableToOrder() {
        int selectedRow = orderProcessingPanel.getOrderTable().getSelectedRow();
        if (selectedRow >= 0) {
            int orderId = (Integer) orderProcessingPanel.getOrderTable().getValueAt(selectedRow, 0);
            int tableId = (Integer) orderProcessingPanel.getTableDropdown().getSelectedItem();

            Order order = orderService.getAllOrders().stream()
                    .filter(o -> o.getOrderId() == orderId)
                    .findFirst()
                    .orElse(null);

            if (order != null) {
                Table table = tableService.getTableById(tableId);
                if (table != null) {
                    table.addOrder(order);
                    tableService.addTable(table); // This automatically saves the table
                    loadOrderTable();
                    JOptionPane.showMessageDialog(orderProcessingPanel, "Order assigned to Table successfully.");
                } else {
                    JOptionPane.showMessageDialog(orderProcessingPanel, "Table not found.");
                }
            } else {
                JOptionPane.showMessageDialog(orderProcessingPanel, "Order not found.");
            }
        } else {
            JOptionPane.showMessageDialog(orderProcessingPanel, "Please select an order to assign to a table.");
        }
    }

    private void deleteOrder() { // New method to delete an order
        int selectedRow = orderProcessingPanel.getOrderTable().getSelectedRow();
        if (selectedRow >= 0) {
            int orderId = (Integer) orderProcessingPanel.getOrderTable().getValueAt(selectedRow, 0);
            orderService.deleteOrder(orderId); // Delete the order using the service
            loadOrderTable(); // Refresh the table display
            JOptionPane.showMessageDialog(orderProcessingPanel, "Order deleted successfully.");
        } else {
            JOptionPane.showMessageDialog(orderProcessingPanel, "Please select an order to delete.");
        }
    }

    private void loadOrderTable() {
        List<Order> orders = orderService.getAllOrders();
        String[] columnNames = {"Order ID", "Food", "Total Price", "Status"};
        Object[][] data = new Object[orders.size()][4];
        for (int i = 0; i < orders.size(); i++) {
            data[i][0] = orders.get(i).getOrderId();
            data[i][1] = orders.get(i).getItems().toString();
            data[i][2] = orders.get(i).getTotalPrice();
            data[i][3] = orders.get(i).getStatus();
        }
        orderProcessingPanel.updateOrderTable(data, columnNames);
    }

    private void populateTableDropdown() {
        List<Table> tables = tableService.getAllTables();
        Integer[] tableIds = tables.stream().map(Table::getTableId).toArray(Integer[]::new);
        orderProcessingPanel.populateTableDropdown(tableIds);
    }
}
