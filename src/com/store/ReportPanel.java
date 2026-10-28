package com.store;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class ReportPanel extends JPanel {
    JTextArea area;

    public ReportPanel() {
        setLayout(new BorderLayout());

        area = new JTextArea();
        area.setFont(new Font("Monospaced", Font.PLAIN, 14));
        area.setEditable(false);
        
        add(new JScrollPane(area), BorderLayout.CENTER);

        JButton refreshBtn = new JButton("Generate Latest Report");
        add(refreshBtn, BorderLayout.SOUTH);

        refreshBtn.addActionListener(e -> generateReport());
        
        generateReport(); // Load it when the tab opens
    }

    private void generateReport() {
        StringBuilder sb = new StringBuilder();
        sb.append("========================================\n");
        sb.append("       STORE MANAGEMENT REPORT          \n");
        sb.append("========================================\n\n");

        try (Connection con = DBConnection.getConnection()) {
            // 1. Basic Counts
            ResultSet rs = con.createStatement().executeQuery("SELECT COUNT(*) c FROM products");
            rs.next(); 
            sb.append("Total Products     : ").append(rs.getInt("c")).append("\n");

            rs = con.createStatement().executeQuery("SELECT COUNT(*) c FROM customers");
            rs.next(); 
            sb.append("Total Customers    : ").append(rs.getInt("c")).append("\n");

            rs = con.createStatement().executeQuery("SELECT COUNT(*) c FROM sales");
            rs.next(); 
            sb.append("Total Sales Count  : ").append(rs.getInt("c")).append("\n");

            rs = con.createStatement().executeQuery("SELECT IFNULL(SUM(total),0) s FROM sales");
            rs.next(); 
            sb.append("Total Revenue      : ₹").append(rs.getDouble("s")).append("\n\n");

            // 2. Low Stock Alerts
            sb.append("--- LOW STOCK ALERTS ---\n");
            rs = con.createStatement().executeQuery("SELECT name, stock FROM products WHERE stock <= low_stock_threshold");
            boolean hasLowStock = false;
            while (rs.next()) {
                hasLowStock = true;
                sb.append("⚠ ").append(rs.getString("name"))
                  .append(" (Only ").append(rs.getInt("stock")).append(" left)\n");
            }
            if (!hasLowStock) sb.append("All stock levels are healthy.\n");
            sb.append("\n");

            // 3. Recent Sales
            sb.append("--- RECENT SALES (Last 5) ---\n");
            String sql = "SELECT p.name, s.quantity, s.total, s.sale_date FROM sales s " +
                         "JOIN products p ON s.product_id=p.id ORDER BY s.id DESC LIMIT 5";
            rs = con.createStatement().executeQuery(sql);
            while (rs.next()) {
                sb.append(rs.getTimestamp("sale_date")).append(" | ")
                  .append(rs.getString("name")).append(" x").append(rs.getInt("quantity"))
                  .append(" = ₹").append(rs.getDouble("total")).append("\n");
            }

        } catch (Exception ex) {
            sb.append("\nError generating report: ").append(ex.getMessage());
        }

        area.setText(sb.toString());
    }
}