package com.store;

import javax.swing.*;
import java.awt.*;

public class DashboardFrame extends JFrame {
    public DashboardFrame(String role) {
        setTitle("Store Management System - Dashboard (" + role + ")");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Create the tabs
        JTabbedPane tabs = new JTabbedPane();
        
        // Add the panels (we will create these files next)
        tabs.addTab("Products", new ProductPanel());
        tabs.addTab("Customers", new CustomerPanel());
        tabs.addTab("Suppliers", new SupplierPanel());
        tabs.addTab("Sales", new SalesPanel());
        tabs.addTab("Purchases", new PurchasePanel());
        tabs.addTab("Reports", new ReportPanel());

        add(tabs);
        setVisible(true);
    }
}