package org.example.restaurant.controller;

/*
Connects the GUI with the SalesReportService.
 */

import org.example.restaurant.service.SalesReportService;
import org.example.restaurant.gui.SalesReportPanel;

public class SalesReportController {
    private SalesReportService salesReportService;
    private SalesReportPanel salesReportPanel;

    public SalesReportController(SalesReportService salesReportService, SalesReportPanel salesReportPanel) {
        this.salesReportService = salesReportService;
        this.salesReportPanel = salesReportPanel;

        // TODO: Set up action listeners for sales report panel
    }

    // TODO: Implement methods to interact with SalesReportService
}