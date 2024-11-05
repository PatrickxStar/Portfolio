package org.example.restaurant.util;

import org.example.restaurant.model.Table;
import org.example.restaurant.service.TableService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doNothing;

public class TableServiceTest {

    private TableService tableService;
    private static final String TEST_CSV_FILE = "src/test/resources/org/example/restaurant/tables_test.csv";

    @Before
    public void setUp() {
        tableService = Mockito.spy(new TableService());

        // Mock the CSV loading and saving to prevent actual file I/O during tests
        doNothing().when(tableService).loadTablesFromCSV();
        doNothing().when(tableService).saveTablesToCSV();

        // Ensure the in-memory list is cleared before each test
        tableService.getAllTables().clear();

        // Clear the CSV file to ensure no persisted data affects the test
        clearCSVFile(TEST_CSV_FILE);
    }

    private void clearCSVFile(String filePath) {
        try {
            File file = new File(filePath);
            if (file.exists()) {
                Files.write(Paths.get(filePath), "".getBytes());
            } else {
                Files.createDirectories(Paths.get(filePath).getParent());
                Files.createFile(Paths.get(filePath));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAddTable() {
        // Ensure the table list is empty before adding
        tableService.getAllTables().clear();

        // Given: a new table to be added
        Table table = new Table(1, "Table 1", 4, "Occupied");

        // When: adding the table
        tableService.addTable(table);

        // Then: the table list should contain exactly one table with the correct name
        List<Table> tables = tableService.getAllTables();
        assertEquals(1, tables.size());  // Expecting 1 table in the list
        assertEquals("Table 1", tables.get(0).getTableName());
    }

    @Test
    public void testDeleteTableByName() {
        // Given: a table to be added and then deleted by name
        Table table = new Table(1, "Table 1", 4, "Occupied");
        tableService.addTable(table);

        // When: deleting the table by name
        tableService.deleteTableByName("Table 1");

        // Then: the table list should be empty
        List<Table> tables = tableService.getAllTables();
        assertTrue(tables.isEmpty());
    }

    @Test
    public void testDeleteTable() {
        // Given: a table to be deleted
        Table table = new Table(1, "Table1", 4, "Available");
        tableService.addTable(table);

        // When: deleting the table
        tableService.deleteTable(table.getTableId());

        // Then: check that the table was deleted
        assertNull(tableService.getTableById(table.getTableId()));
    }

    @Test
    public void testGetTableById() {
        // Given: a new table to be retrieved by ID
        Table table = new Table(1, "Table 1", 4, "Occupied");
        tableService.addTable(table);

        // When: retrieving the table by ID
        Table retrievedTable = tableService.getTableById(1);

        // Then: the retrieved table should match the one that was added
        assertNotNull(retrievedTable);
        assertEquals("Table 1", retrievedTable.getTableName());
    }
}
