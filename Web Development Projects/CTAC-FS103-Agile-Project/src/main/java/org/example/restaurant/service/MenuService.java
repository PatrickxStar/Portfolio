package org.example.restaurant.service;

import org.example.restaurant.model.MenuItem;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MenuService {
    private List<MenuItem> menuItems;
    private static final String MENU_CSV_FILE = "src/main/resources/org/example/restaurant/menuItems.csv";

    public MenuService() {
        menuItems = new ArrayList<>();
        loadMenuItemsFromCSV(); // Load existing menu items from a CSV file
    }

    public void addMenuItem(MenuItem item) {
        if (findMenuItemByName(item.getName()) != null) {
            throw new IllegalArgumentException("Menu item with this name already exists.");
        }
        menuItems.add(item);
        saveMenuItemsToCSV(); // Save the updated menu to a CSV file
    }

    public void removeMenuItem(String itemName) {
        MenuItem item = findMenuItemByName(itemName);
        if (item != null) {
            menuItems.remove(item);
            saveMenuItemsToCSV(); // Save the updated menu to a CSV file
        } else {
            throw new IllegalArgumentException("Menu item not found.");
        }
    }

    public void editMenuItem(MenuItem updatedItem) {
        for (int i = 0; i < menuItems.size(); i++) {
            MenuItem item = menuItems.get(i);
            if (item.getName().equalsIgnoreCase(updatedItem.getName())) {
                menuItems.set(i, updatedItem);
                saveMenuItemsToCSV(); // Save the updated menu to a CSV file
                return;
            }
        }
        throw new IllegalArgumentException("Menu item not found.");
    }

    public List<MenuItem> getAllMenuItems() {
        return new ArrayList<>(menuItems); // Return a copy of the list
    }

    public MenuItem findMenuItemByName(String itemName) {
        for (MenuItem item : menuItems) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return item;
            }
        }
        return null;
    }

    public void refreshMenuItems() {
        menuItems.clear();
        loadMenuItemsFromCSV();
    }

    private void saveMenuItemsToCSV() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(MENU_CSV_FILE))) {
            for (MenuItem item : menuItems) {
                bw.write(item.getName() + "," + item.getDescription() + "," + item.getPrice() + "," +
                        String.join(";", item.getIngredients()));
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadMenuItemsFromCSV() {
        try (BufferedReader br = new BufferedReader(new FileReader(MENU_CSV_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",", 4);
                String name = values[0];
                String description = values[1];
                double price = Double.parseDouble(values[2]);
                List<String> ingredients = List.of(values[3].split(";"));
                menuItems.add(new MenuItem(name, description, price, ingredients));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
