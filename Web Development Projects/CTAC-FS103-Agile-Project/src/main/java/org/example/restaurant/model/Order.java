package org.example.restaurant.model;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class Order {
    private int orderId;
    private int tableId;
    private Map<String, Integer> items;  // Item name to quantity
    private double totalPrice;
    private String status;  // New field to track the status of the order
    private DecimalFormat df = new DecimalFormat("####0.00");

    // Constructor
    public Order(int orderId, int tableId, double totalPrice) {
        this.orderId = orderId;
        this.tableId = tableId;
        this.items = new HashMap<>();
        this.totalPrice = Double.parseDouble(df.format(totalPrice));
        this.status = "New";  // Default status
    }

    // Getters and Setters
    public int getOrderId() {
        return orderId;
    }

    public int getTableId() {
        return tableId;
    }

    public Map<String, Integer> getItems() {
        return items;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Method to add items to the order
    public void addItem(String itemName, int quantity, double pricePerUnit) {
        items.put(itemName, items.getOrDefault(itemName, 0) + quantity);
        totalPrice += quantity * pricePerUnit;
    }

    // Optional: ToString method for better readability when displaying order information
    @Override
    public String toString() {
        return "Order ID: " + orderId + ", Table ID: " + tableId + ", Items: " + items + ", Total Price: $" + totalPrice + ", Status: " + status;
    }
}

