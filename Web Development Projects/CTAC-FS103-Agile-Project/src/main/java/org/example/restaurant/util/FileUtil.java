package org.example.restaurant.util;

/*
Utility class for file handling (loading and saving data).
 */

import org.example.restaurant.model.MenuItem;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileUtil {

    /**
    Writes data content to a CSV file
     @param filePath The path of the file location
     @param data A List of Strings, written line-by-line in the buffered writer
     */
    public static void writeToCSV(String filePath, List<String[]> data) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String[] line : data) {
                writer.write(String.join(",", line));
                writer.newLine();
            }
        }
    }

    /**
     Reads data content to a CSV file
     @param filePath The path of the file location
     @return List of Iterated Strings, split using a comma
     */
    public static List<String[]> readFromCSV(String filePath) throws IOException {
        List<String[]> data = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] lineData = line.split(",");
                data.add(lineData);
            }
        }
        return data;
    }

    // Read's menu items as the above methods do
    public List<MenuItem> readMenuItemsFromCSV(String filePath) throws IOException {
        List<MenuItem> menuItems = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(","); // Assuming CSV is comma-separated
                String name = values[0];
                String description = values[1];
                double price = Double.parseDouble(values[2]);
                List<String> ingredients = Arrays.asList(values[3].split(";")); // Assuming ingredients are separated by semicolons
                menuItems.add(new MenuItem(name, description, price, ingredients));
            }
        }
        return menuItems;
    }
}
