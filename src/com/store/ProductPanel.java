package com.store;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ProductPanel extends JPanel {
    JTable table;
    DefaultTableModel model;

    public ProductPanel() {
        setLayout(new BorderLayout());

        // Form to add products
        JPanel form = new JPanel(new GridLayout(2, 4, 5, 5)); 
        JTextField nameF = new JTextField();
        JTextField priceF = new JTextField();
        JTextField stockF = new JTextField();
        JTextField threshF = new JTextField("10");
        
        form.add(new JLabel("Name:"));
        form.add(new JLabel("Price:"));
        form.add(new JLabel("Stock:"));
        form.add(new JLabel("Low Threshold:"));
        
        form.add(nameF); 
        form.add(priceF); 
        form.add(stockF); 
        form.add(threshF);

        // Put the form and the button in a top panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(form, BorderLayout.CENTER);
        
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton addBtn = new JButton("Add Product");
        btnPanel.add(addBtn);
        topPanel.add(btnPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);

        // Table to show products
        model = new DefaultTableModel(new String[]{"ID", "Name", "Price", "Stock", "Threshold"}, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton refresh = new JButton("Refresh / Low-Stock Alert");
        add(refresh, BorderLayout.SOUTH);

        // Button actions
        addBtn.addActionListener(e -> {
            try (Connection con = DBConnection.getConnection()) {
                PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO products (name, price, stock, low_stock_threshold) VALUES (?,?,?,?)");
                ps.setString(1, nameF.getText());
                ps.setDouble(2, Double.parseDouble(priceF.getText()));
                ps.setInt(3, Integer.parseInt(stockF.getText()));
                ps.setInt(4, Integer.parseInt(threshF.getText()));
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Product added");
                nameF.setText(""); priceF.setText(""); stockF.setText(""); threshF.setText("10");
                loadProducts();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        refresh.addActionListener(e -> {
            loadProducts();
            showLowStock();
        });

        loadProducts();
    }

    private void loadProducts() {
        model.setRowCount(0);
        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM products")) {
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id"), rs.getString("name"),
                    rs.getDouble("price"), rs.getInt("stock"),
                    rs.getInt("low_stock_threshold")
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void showLowStock() {
        StringBuilder sb = new StringBuilder();
        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT name, stock FROM products WHERE stock <= low_stock_threshold")) {
            while (rs.next()) {
                sb.append(rs.getString("name")).append(": ").append(rs.getInt("stock")).append("\n");
            }
        } catch (Exception ignored) {}
        
        if (sb.length() > 0) JOptionPane.showMessageDialog(this, "LOW STOCK:\n" + sb);
        else JOptionPane.showMessageDialog(this, "All stock levels OK");
    }
}