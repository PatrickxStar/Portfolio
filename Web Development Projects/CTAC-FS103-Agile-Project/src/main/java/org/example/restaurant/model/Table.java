package org.example.restaurant.model;

import java.util.ArrayList;
import java.util.List;

public class Table {
    private int tableId;
    private String tableName;
    private int occupants;
    private String status;
    private double totalPrice;
    private List<Order> orders;  // List to store orders associated with the table

    public Table(int tableId, String tableName, int occupants, String status) {
        this.tableId = tableId;
        this.tableName = tableName;
        this.occupants = occupants;
        this.status = status;
        this.totalPrice = 0.0;
        this.orders = new ArrayList<>();
    }

    // Getters and setters
    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public int getOccupants() {
        return occupants;
    }

    public void setOccupants(int occupants) {
        this.occupants = occupants;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void addOrder(Order order) {
        orders.add(order);
        updateTotalPrice();
    }

    public void removeOrder(Order order) {
        orders.remove(order);
        updateTotalPrice();
    }

    private void updateTotalPrice() {
        totalPrice = orders.stream().mapToDouble(Order::getTotalPrice).sum();
    }

    // This is the method you need for your SalesReportService
    public double getTotalSales() {
        return totalPrice;
    }
}
