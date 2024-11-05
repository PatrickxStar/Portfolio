package org.example.restaurant.service;

/*
Generates and exports sales reports.
 */

import org.example.restaurant.model.MenuItem;
import org.example.restaurant.model.Order;
import org.example.restaurant.model.Table;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class SalesReportService {
    private List<Order> orders;
    private List<MenuItem> menuItems;

    public SalesReportService(List<Order> orders, List<MenuItem> menuItems) {
        this.orders = orders;
        this.menuItems = menuItems;
    }

    public String generateDailySalesReport(TableService tableService) {
        // Initialize the report string with a header
        StringBuilder report = new StringBuilder();
        report.append("-----------------------------\n");
        report.append("Daily Sales Report\n");

        // Add the current date
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        report.append("Date: ").append(date).append("\n");
        report.append("-----------------------------\n");

        // Calculate total revenue
        double totalRevenue = 0;
        for (Order order : orders) {
            totalRevenue += order.getTotalPrice();
        }
        report.append("Total Revenue: $").append(new DecimalFormat("#0.00").format(totalRevenue)).append("\n\n");

        // Get and display the most popular items
        report.append("Most Popular Items:\n");
        List<Map.Entry<String, Integer>> mostPopularItems = getTopThreeBestSellingItems(orders);
        for (int i = 0; i < mostPopularItems.size(); i++) {
            Map.Entry<String, Integer> entry = mostPopularItems.get(i);
            report.append(i + 1).append(". ").append(entry.getKey()).append(": ").append(entry.getValue()).append(" orders\n");
        }
        report.append("\n");

        // Calculate and display table sales
        report.append("Table Sales:\n");
        List<Table> tables = tableService.getAllTables();
        tables.sort((t1, t2) -> Double.compare(t2.getTotalSales(), t1.getTotalSales()));  // Sort tables by total sales
        for (int i = 0; i < Math.min(tables.size(), 3); i++) {  // Display top 3 tables
            Table table = tables.get(i);
            report.append(i + 1).append(". Table ").append(table.getTableId()).append(": $").append(new DecimalFormat("#0.00").format(table.getTotalSales())).append("\n");
        }
        report.append("\n");

        // Display detailed orders
        report.append("Detailed Orders:\n");
        for (Order order : orders) {
            report.append("Order ID: #").append(order.getOrderId()).append("\n");
            report.append("Table ID: ").append(order.getTableId()).append("\n");
            report.append("Items:\n");

            // Iterate through the HashMap<String, Integer> to display each item and its quantity
            for (Map.Entry<String, Integer> entry : order.getItems().entrySet()) {
                String itemName = entry.getKey();
                int quantity = entry.getValue();

                // Assuming you have a way to retrieve the MenuItem object by name
                MenuItem item = findMenuItemByName(itemName);  // Implement this method to fetch the MenuItem by name

                if (item != null) {
                    double itemPrice = item.getPrice() * quantity;
                    report.append("  - ").append(itemName).append(": ").append(quantity).append(" ($").append(new DecimalFormat("#0.00").format(itemPrice)).append(")\n");
                }
            }
            report.append("Total: $").append(new DecimalFormat("#0.00").format(order.getTotalPrice())).append("\n\n");
        }

        // Return the generated report
        return report.toString();
    }


    public List<Map.Entry<String, Integer>> getTopThreeBestSellingItems(List<Order> orders) {
        // Map to store item name and its total count
        Map<String, Integer> itemCountMap = new HashMap<>();

        // Iterate through each order
        for (Order order : orders) {
            // Iterate through each entry in the HashMap<String, Integer>
            for (Map.Entry<String, Integer> entry : order.getItems().entrySet()) {
                String itemName = entry.getKey();
                int quantity = entry.getValue();

                // Update the item count in the map
                itemCountMap.put(itemName, itemCountMap.getOrDefault(itemName, 0) + quantity);
            }
        }

        // Convert the map entries to a list and sort by count in descending order
        List<Map.Entry<String, Integer>> sortedItems = new ArrayList<>(itemCountMap.entrySet());
        sortedItems.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

        // Return the top three items (or fewer if there are less than three)
        return sortedItems.subList(0, Math.min(3, sortedItems.size()));
    }

    public MenuItem findMenuItemByName(String itemName) {
        for (MenuItem item : menuItems) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return item;
            }
        }
        return null;  // Return null if the item is not found
    }

    public void exportReportToFile(String report, String filePath) {
        // Export the report to a text file
        // Create a FileWriter in try-with-resources to ensure the file is closed properly
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(report);  // Write the report string to the file
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
