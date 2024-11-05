package org.example.restaurant.gui;

import org.example.restaurant.controller.InventoryController;
import org.example.restaurant.controller.OrderController;
import org.example.restaurant.controller.TableController;
import org.example.restaurant.model.User;
import org.example.restaurant.service.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    private JMenuBar menuBar;
    private JPanel contentPanel;

    private JPanel mainPanel;
    private JPanel greetingPanel;
    private JLabel greetingLabel;
    private JButton inventoryButton;
    private JButton ringInOrdersButton;
    private JButton salesReportsButton;
    private JButton manageTableButton;

    private InventoryManagementPanel inventoryManagementPanel;
    private OrderProcessingPanel orderProcessingPanel;
    private TableManagementPanel tableManagementPanel;
    private SalesReportPanel salesReportPanel;
    private MenuManagementPanel menuManagementPanel;
    private JMenu backMenu;

    private InventoryService inventoryService;
    private OrderService orderService;
    private SalesReportService salesReportService;
    private TableService tableService;
    private MenuService menuService =  new MenuService();
    private UserService userService =  new UserService();
    private String userName;

    public MainFrame(String username) {
        // Initialize components
        initializeComponents(username);

        // Set up menus and their action listeners
        setupMenu();

        // Add components to frame
        setupFrame();

        // Set up inventory management
        setupInventoryManagement();

        // Set up order processing
        setupOrderProcessing();

        // Set up table management
        setupTableManagement();

        // Set up Sales Report
        setupSalesReport();

        // Set pu Menu Management
        setupMenuManagement();
    }

    // Initialize components
    private void initializeComponents(String username) {
        userName = username;
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        greetingPanel = new JPanel();
        greetingPanel.setLayout(new FlowLayout());

        greetingLabel = new JLabel("Welcome to Restaurant Management System, " + username + "!");
        greetingLabel.setFont(new Font("Arial", Font.BOLD, 18));
        greetingPanel.add(greetingLabel);

        mainPanel.add(greetingPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1));

        inventoryButton = new JButton("Inventory Management");
        inventoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(userService.findUserByUsername(userName).getRole().equals("manager")) {
                    setupInventoryManagement();
                    showPanel("Inventory");
                }
                else{
                    JOptionPane.showMessageDialog(null, "User must be a manager!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        ringInOrdersButton = new JButton("Ring in Orders");
        ringInOrdersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setupOrderProcessing();
                showPanel("Orders");
            }
        });

        salesReportsButton = new JButton("View Sales Reports");
        salesReportsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(userService.findUserByUsername(userName).getRole().equals("manager")) {
                    showPanel("Sales");
                }
                else{
                    JOptionPane.showMessageDialog(null, "User must be a manager!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        manageTableButton = new JButton("Manage Table");
        manageTableButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPanel("ManageTable");
                // Revalidate and repaint to ensure the UI is updated properly
                contentPanel.revalidate();
                contentPanel.repaint();
            }
        });


        buttonPanel.add(ringInOrdersButton);
        buttonPanel.add(manageTableButton);
        buttonPanel.add(inventoryButton);
        buttonPanel.add(salesReportsButton);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        contentPanel = new JPanel(new CardLayout());
        contentPanel.add(mainPanel, "Main");

        add(contentPanel);
    }

    // Set up inventory management
    private void setupInventoryManagement() {
        inventoryService = new InventoryService();
        inventoryManagementPanel = new InventoryManagementPanel();
        InventoryController inventoryController = new InventoryController(inventoryService, inventoryManagementPanel);

        addPanel(inventoryManagementPanel, "Inventory");
    }

    // Set up order processing
    private void setupOrderProcessing() {
        orderService = new OrderService();
        tableService = new TableService(); // Make sure this is initialized before passing it
        orderProcessingPanel = new OrderProcessingPanel();
        OrderController orderController = new OrderController(orderService, tableService, orderProcessingPanel);

        addPanel(orderProcessingPanel, "Orders");
    }


    // Set up table management
    private void setupTableManagement() {
        tableService = new TableService();
        tableManagementPanel = new TableManagementPanel();
        TableController tableController = new TableController(tableService, tableManagementPanel);

        addPanel(tableManagementPanel, "ManageTable");
    }

    // Set up Sales Report
    private void setupSalesReport(){
        SalesReportService salesReportService = new SalesReportService(orderService.getAllOrders(), menuService.getAllMenuItems());
        salesReportPanel = new SalesReportPanel(salesReportService, tableService);

        addPanel(salesReportPanel, "Sales");
    }

    // Set up Menu Management
    private void setupMenuManagement(){
        MenuService menuService = new MenuService();
        menuManagementPanel = new MenuManagementPanel(menuService);

        addPanel(menuManagementPanel, "Menu");
    }

    // Set up menus and their action listeners
    private void setupMenu() {
        menuBar = new JMenuBar();

        JMenu menu = new JMenu("Options");
        JMenuItem inventoryMenuItem = new JMenuItem("Inventory Management");
        JMenuItem orderMenuItem = new JMenuItem("Ring in Orders");
        JMenuItem reportsMenuItem = new JMenuItem("View Sales Reports");
        JMenuItem manageTableMenuItem = new JMenuItem("Manage Table");
        JMenuItem manageMenuItems = new JMenuItem("Manage Menu");
        JMenuItem createUserMenuItem = new JMenuItem("Create User");
        JMenuItem exitMenuItem = new JMenuItem("Exit");

        inventoryMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(userService.findUserByUsername(userName).getRole().equals("manager")) {
                    showPanel("Inventory");
                }
                else{
                    JOptionPane.showMessageDialog(null, "User must be a manager!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        orderMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPanel("Orders");
            }
        });

        reportsMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(userService.findUserByUsername(userName).getRole().equals("manager")) {
                    showPanel("Sales");
                }
                else{
                    JOptionPane.showMessageDialog(null, "User must be a manager!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        manageTableMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPanel("ManageTable");
                // Revalidate and repaint to ensure the UI is updated properly
                contentPanel.revalidate();
                contentPanel.repaint();
            }
        });

        manageMenuItems.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPanel("Menu");
                // Revalidate and repaint to ensure the UI is updated properly
                contentPanel.revalidate();
                contentPanel.repaint();
            }
        });

        createUserMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserService userService = new UserService();
                userService.createUser();
            }
        });

        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        menu.add(inventoryMenuItem);
        menu.add(orderMenuItem);
        menu.add(reportsMenuItem);
        menu.add(manageTableMenuItem);
        menu.add(manageMenuItems);
        menu.add(createUserMenuItem);
        menu.addSeparator();
        menu.add(exitMenuItem);

        menuBar.add(menu);

        // Add the back button as a JMenu to match the style of the Options menu
        backMenu = new JMenu("Back");
        backMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                showPanel("Main");
            }
        });

        menuBar.add(Box.createHorizontalGlue()); // Pushes the back button to the right side
        menuBar.add(backMenu);

        setJMenuBar(menuBar);
    }

    // Set frame properties
    private void setupFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Implement methods to switch between different panels
    private void showPanel(String panelName) {
        CardLayout cardLayout = (CardLayout) contentPanel.getLayout();
        cardLayout.show(contentPanel, panelName);
    }

    public void addPanel(JPanel panel, String panelName) {
        contentPanel.add(panel, panelName);
    }
}
