package org.example.restaurant.util;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("File Utility Tests")
class FileUtilTests {

    private static final String TEST_CSV_FILE_PATH = "testdata.csv";

    @BeforeEach
    void setUp() throws IOException {
        // Prepare test data to write to the CSV file
        List<String[]> testData = new ArrayList<>();
        testData.add(new String[]{"Name", "Age", "Occupation"});
        testData.add(new String[]{"Alice", "30", "Engineer"});
        testData.add(new String[]{"Bob", "25", "Doctor"});

        // Write test data to the CSV file
        FileUtil.writeToCSV(TEST_CSV_FILE_PATH, testData);
    }

    @AfterEach
    void tearDown() {
        // Delete the test CSV file after each test
        File file = new File(TEST_CSV_FILE_PATH);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void testWriteToCSV() throws IOException {
        // Prepare new data to write to the CSV file
        List<String[]> newData = new ArrayList<>();
        newData.add(new String[]{"Name", "Age", "Occupation"});
        newData.add(new String[]{"Charlie", "28", "Teacher"});

        // Write new data to the CSV file
        FileUtil.writeToCSV(TEST_CSV_FILE_PATH, newData);

        // Read the file content to verify it matches the new data
        List<String[]> fileContent = FileUtil.readFromCSV(TEST_CSV_FILE_PATH);

        // Verify that the file content is as expected
        assertNotNull(fileContent);
        assertEquals(2, fileContent.size());
        assertArrayEquals(new String[]{"Charlie", "28", "Teacher"}, fileContent.get(1));
    }

    @Test
    void testReadFromCSV() throws IOException {
        // Read content from the CSV file
        List<String[]> content = FileUtil.readFromCSV(TEST_CSV_FILE_PATH);

        // Verify the content read from the file matches the expected data
        assertNotNull(content);
        assertEquals(3, content.size());  // 3 lines in the CSV file

        // Check the first line (header)
        assertArrayEquals(new String[]{"Name", "Age", "Occupation"}, content.get(0));

        // Check the second line (first data row)
        assertArrayEquals(new String[]{"Alice", "30", "Engineer"}, content.get(1));

        // Check the third line (second data row)
        assertArrayEquals(new String[]{"Bob", "25", "Doctor"}, content.get(2));
    }

    @Test
    void testReadFromCSVFileNotFound() {
        // Attempt to read from a non-existent file
        String nonExistentFilePath = "nonexistentfile.csv";

        Exception exception = assertThrows(IOException.class, () -> FileUtil.readFromCSV(nonExistentFilePath));

        // Verify that the exception message is correct
        String expectedMessage = "nonexistentfile.csv (The system cannot find the file specified)";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}