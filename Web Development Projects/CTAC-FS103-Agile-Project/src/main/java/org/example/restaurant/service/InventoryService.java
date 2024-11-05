package org.example.restaurant.service;

import org.example.restaurant.model.InventoryItem;
import org.example.restaurant.model.MenuItem;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class InventoryService {
    private List<InventoryItem> inventory;
    private static final String INVENTORY_CSV_FILE = "src/main/resources/org/example/restaurant/inventoryItems.csv";

    public InventoryService() {
        inventory = new ArrayList<>();
        loadInventoryFromCSV();  // Load existing inventory items from a CSV file
    }

    public void restockItem(String ingredientName, int quantity) {
        for (InventoryItem item : inventory) {
            if (item.getIngredientName().equalsIgnoreCase(ingredientName)) {
                item.setQuantity(item.getQuantity() + quantity);
                saveInventoryToCSV();  // Save updated inventory to the CSV file
                return;
            }
        }
        inventory.add(new InventoryItem(ingredientName, quantity));
        saveInventoryToCSV();  // Save updated inventory to the CSV file
    }

    public InventoryItem getItemByName(String ingredientName) {
        for (InventoryItem item : inventory) {
            if (item.getIngredientName().equalsIgnoreCase(ingredientName)) {
                return item;
            }
        }
        return null;
    }

    public List<InventoryItem> getAllItems() {
        return new ArrayList<>(inventory);
    }

    public Object[][] getInventoryData() {
        Object[][] data = new Object[inventory.size()][2];
        for (int i = 0; i < inventory.size(); i++) {
            InventoryItem item = inventory.get(i);
            data[i][0] = item.getIngredientName();
            data[i][1] = item.getQuantity();
        }
        return data;
    }

    public void addIngredientsToInventory(List<String> ingredients) {
        for (String ingredient : ingredients) {
            InventoryItem item = new InventoryItem(ingredient, 0);
            inventory.add(item);
            restockItem(ingredient, 1);  // Increment quantity by 1 for each ingredient added to inventory
        }
        saveInventoryToCSV();
        loadInventoryFromCSV();
    }

    // Method to save inventory items to a CSV file
    private void saveInventoryToCSV() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(INVENTORY_CSV_FILE))) {
            for (InventoryItem item : inventory) {
                bw.write(item.getIngredientName() + "," + item.getQuantity());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to load inventory items from a CSV file
    private void loadInventoryFromCSV() {
        try (BufferedReader br = new BufferedReader(new FileReader(INVENTORY_CSV_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",", 2);
                String ingredientName = values[0];
                int quantity = Integer.parseInt(values[1]);
                inventory.add(new InventoryItem(ingredientName, quantity));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reduceIngredientsForMenuItem(MenuItem menuItem) {
        List<String> ingredients = menuItem.getIngredients();

        for (String ingredient : ingredients) {
            InventoryItem inventoryItem = getItemByName(ingredient);

            if (inventoryItem != null && inventoryItem.getQuantity() > 0) {
                inventoryItem.setQuantity(inventoryItem.getQuantity() - 1);

                // Optional: If quantity reaches 0, you could log it or notify the user
                if (inventoryItem.getQuantity() <= 0) {
                    System.out.println("Ingredient " + ingredient + " is out of stock.");
                }
            } else {
                // Handle case where ingredient is not found or already at 0 quantity
                System.out.println("Ingredient " + ingredient + " is not available or out of stock.");
            }
        }

        // Save the updated inventory back to the CSV file
        saveInventoryToCSV();
    }
}
