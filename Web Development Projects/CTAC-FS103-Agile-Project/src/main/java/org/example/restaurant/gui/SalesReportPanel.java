package org.example.restaurant.gui;

/*
Interface for viewing and exporting sales reports.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.example.restaurant.service.SalesReportService;
import org.example.restaurant.service.TableService;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class SalesReportPanel extends JPanel {
    private JTextArea reportTextArea;
    private JButton generateReportButton;
    private JButton exportReportButton;
    private SalesReportService salesReportService;
    private TableService tableService;

    public SalesReportPanel(SalesReportService salesReportService, TableService tableService) {
        this.salesReportService = salesReportService;
        this.tableService = tableService;

        setLayout(new BorderLayout());

        // Initialize the text area for displaying the report
        reportTextArea = new JTextArea(20, 50);  // Set preferred size
        reportTextArea.setEditable(false);  // Make the text area read-only
        JScrollPane scrollPane = new JScrollPane(reportTextArea);  // Add scroll pane
        add(scrollPane, BorderLayout.CENTER);

        // Initialize the buttons
        generateReportButton = new JButton("Generate Report");
        exportReportButton = new JButton("Export Report");

        // Panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(generateReportButton);
        buttonPanel.add(exportReportButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add action listeners for the buttons
        generateReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateReport();
            }
        });

        exportReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportReport();
            }
        });
    }

    private void generateReport() {
        // Generate the report using SalesReportService
        String report = salesReportService.generateDailySalesReport(tableService);
        // Display the report in the text area
        reportTextArea.setText(report);
    }

    private void exportReport() {
        // Get the report text
        String report = reportTextArea.getText();

        // Check if there is a report to export
        if (report.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No report to export. Please generate a report first.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Ask the user to select a file location
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Report");
        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            // Export the report to the selected file
            salesReportService.exportReportToFile(report, fileToSave.getAbsolutePath());
            JOptionPane.showMessageDialog(this, "Report exported successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
