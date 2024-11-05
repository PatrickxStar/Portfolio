package org.example.restaurant.service;

import org.example.restaurant.model.Order;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class OrderService {
    private List<Order> orders;
    private static final String ORDERS_CSV_FILE = "src/main/resources/org/example/restaurant/orders.csv";

    public OrderService() {
        orders = new ArrayList<>();
        loadOrdersFromCSV(); // Load existing orders from a CSV file
    }

    public void addOrder(Order order) {
        orders.add(order);
        saveOrdersToCSV(); // Save the updated orders list to a CSV file
    }

    public void updateOrderStatus(int orderId, String status) {
        for (Order order : orders) {
            if (order.getOrderId() == orderId) {
                order.setStatus(status);
                saveOrdersToCSV(); // Save the updated orders list to a CSV file
                break;
            }
        }
    }

    public void deleteOrder(int orderId) { // New method to delete an order
        orders.removeIf(order -> order.getOrderId() == orderId);
        saveOrdersToCSV(); // Save the updated orders list to a CSV file
    }

    public List<Order> getAllOrders() {
        return new ArrayList<>(orders); // Return a copy of the list
    }

    public void saveOrdersToCSV() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ORDERS_CSV_FILE))) {
            for (Order order : orders) {
                bw.write(order.getOrderId() + "," + order.getTableId() + "," + convertItemsToString(order.getItems()) + "," +
                        order.getTotalPrice() + "," + order.getStatus());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace(); // Replace with logging if preferred
        }
    }

    public void loadOrdersFromCSV() {
        try (BufferedReader br = new BufferedReader(new FileReader(ORDERS_CSV_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",", 5); // Updated to expect 5 columns now
                if (values.length >= 5) {
                    try {
                        int orderId = Integer.parseInt(values[0].trim());
                        int tableId = Integer.parseInt(values[1].trim());
                        Map<String, Integer> items = parseItemsString(values[2]);
                        double totalPrice = Double.parseDouble(values[3].trim());
                        String status = values[4].trim();
                        Order order = new Order(orderId, tableId, totalPrice);
                        order.getItems().putAll(items); // Add the items to the order
                        order.setStatus(status);
                        orders.add(order);
                    } catch (NumberFormatException e) {
                        System.err.println("Skipping invalid line in CSV due to number format issue: " + line);
                    }
                } else {
                    System.err.println("Skipping invalid line in CSV: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // Replace with logging if preferred
        }
    }

    public String convertItemsToString(Map<String, Integer> items) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Integer> entry : items.entrySet()) {
            sb.append(entry.getKey()).append(":").append(entry.getValue()).append(";");
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1); // Remove last semicolon
        }
        return sb.toString();
    }

    public Map<String, Integer> parseItemsString(String itemsString) {
        Map<String, Integer> items = new HashMap<>();
        String[] itemPairs = itemsString.split(";");
        for (String pair : itemPairs) {
            String[] itemData = pair.split(":");
            if (itemData.length == 2) { // Check if the split resulted in two parts
                try {
                    String itemName = itemData[0].trim();
                    int quantity = Integer.parseInt(itemData[1].trim());
                    items.put(itemName, quantity);
                } catch (NumberFormatException e) {
                    System.err.println("Invalid quantity format in pair: " + pair);
                }
            } else {
                System.err.println("Skipping invalid item pair: " + pair);
            }
        }
        return items;
    }
}
