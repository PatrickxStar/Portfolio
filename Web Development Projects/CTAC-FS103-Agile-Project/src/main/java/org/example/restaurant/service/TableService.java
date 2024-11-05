package org.example.restaurant.service;

import org.example.restaurant.model.Table;
import org.example.restaurant.model.Order;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TableService {
    private List<Table> tables;
    private static final String TABLE_CSV_FILE = "src/main/resources/org/example/restaurant/tables.csv";

    public TableService() {
        tables = new ArrayList<>();
        loadTablesFromCSV();
    }

    public void addTable(Table table) {
        // Check if table with the same ID already exists
        for (Table existingTable : tables) {
            if (existingTable.getTableId() == table.getTableId()) {
                // Update existing table
                existingTable.setTableName(table.getTableName());
                existingTable.setOccupants(table.getOccupants());
                existingTable.setStatus(table.getStatus());
                existingTable.setTotalPrice(table.getTotalPrice());
                saveTablesToCSV();  // Save updated list
                return;
            }
        }
        // If no matching ID, add a new table
        tables.add(table);
        saveTablesToCSV();  // Save updated list
    }

    public void deleteTable(int tableId) {
        tables.removeIf(table -> table.getTableId() == tableId);
        saveTablesToCSV();  // Save updated list
    }

    public void deleteTableByName(String tableName) {
        tables.removeIf(table -> table.getTableName().equalsIgnoreCase(tableName));
        saveTablesToCSV();  // Save updated list
    }


    public List<Table> getAllTables() {
        return new ArrayList<>(tables);  // Return a copy of the list
    }

    public Table getTableById(int tableId) {
        return tables.stream()
                .filter(table -> table.getTableId() == tableId)
                .findFirst()
                .orElse(null);
    }

    public void addOrderToTable(int tableId, Order order) {
        Table table = getTableById(tableId);
        if (table != null) {
            table.addOrder(order);
            saveTablesToCSV();  // Save updated list
        }
    }

    public void saveTablesToCSV() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(TABLE_CSV_FILE))) {
            for (Table table : tables) {
                bw.write(table.getTableId() + "," + table.getTableName() + "," +
                        table.getOccupants() + "," + table.getStatus() + "," +
                        table.getTotalPrice());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();  // Replace with logging if preferred
        }
    }

    public void loadTablesFromCSV() {
        try (BufferedReader br = new BufferedReader(new FileReader(TABLE_CSV_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",", 5); // Ensure there are exactly 5 values
                if (values.length >= 5) {
                    try {
                        int tableId = Integer.parseInt(values[0].trim());
                        String tableName = values[1].trim();
                        int occupants = Integer.parseInt(values[2].trim());
                        String status = values[3].trim();
                        double totalPrice = Double.parseDouble(values[4].trim());

                        Table table = new Table(tableId, tableName, occupants, status);
                        table.setTotalPrice(totalPrice);
                        tables.add(table);
                    } catch (NumberFormatException e) {
                        System.err.println("Skipping invalid line in CSV: " + line);
                    }
                } else {
                    System.err.println("Skipping invalid line in CSV: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();  // Replace with logging if preferred
        }
    }
}
