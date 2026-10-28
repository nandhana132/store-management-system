package com.store;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class SalesPanel extends JPanel {
    JComboBox<String> productBox, customerBox;
    JTextField qtyF, totalF;
    JTable table;
    DefaultTableModel model;
    
    // These will hold the database IDs for the dropdowns
    Map<String, Integer> productMap = new HashMap<>();
    Map<String, Integer> customerMap = new HashMap<>();
    Map<String, Double> priceMap = new HashMap<>();

    public SalesPanel() {
        setLayout(new BorderLayout());

        // The Form
        JPanel form = new JPanel(new GridLayout(2, 5, 5, 5));
        productBox = new JComboBox<>();
        customerBox = new JComboBox<>();
        qtyF = new JTextField();
        totalF = new JTextField();
        totalF.setEditable(false); // Auto-calculated, so user can't type here
        
        JButton calcBtn = new JButton("Calculate Bill");
        JButton sellBtn = new JButton("Record Sale");

        form.add(new JLabel("Select Product:")); form.add(new JLabel("Select Customer:"));
        form.add(new JLabel("Quantity:")); form.add(new JLabel("Total Bill (₹):")); form.add(new JLabel(""));
        
        form.add(productBox); form.add(customerBox);
        form.add(qtyF); form.add(totalF); form.add(calcBtn);
        
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(form, BorderLayout.NORTH);
        
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.add(sellBtn);
        topPanel.add(btnPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);

        // The Table for Sales History
        model = new DefaultTableModel(new String[]{"Sale ID", "Product", "Customer", "Quantity", "Total Bill", "Date & Time"}, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Action Listeners
        calcBtn.addActionListener(e -> calculateTotal());
        sellBtn.addActionListener(e -> recordSale());
        
        loadCombos();
        loadSalesHistory();
    }

    private void loadCombos() {
        try (Connection con = DBConnection.getConnection()) {
            // Load Products
            ResultSet rs = con.createStatement().executeQuery("SELECT id, name, price FROM products WHERE stock > 0");
            productBox.removeAllItems(); productMap.clear(); priceMap.clear();
            while (rs.next()) {
                String label = rs.getString("name");
                productBox.addItem(label);
                productMap.put(label, rs.getInt("id"));
                priceMap.put(label, rs.getDouble("price"));
            }

            // Load Customers
            rs = con.createStatement().executeQuery("SELECT id, name FROM customers");
            customerBox.removeAllItems(); customerMap.clear();
            while (rs.next()) {
                String label = rs.getString("name");
                customerBox.addItem(label);
                customerMap.put(label, rs.getInt("id"));
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error loading data: " + ex.getMessage());
        }
    }

    private void calculateTotal() {
        try {
            String p = (String) productBox.getSelectedItem();
            int qty = Integer.parseInt(qtyF.getText());
            double total = priceMap.get(p) * qty;
            totalF.setText(String.format("%.2f", total));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid quantity.");
        }
    }

    private void recordSale() {
        try (Connection con = DBConnection.getConnection()) {
            String p = (String) productBox.getSelectedItem();
            String c = (String) customerBox.getSelectedItem();
            int pid = productMap.get(p);
            int cid = customerMap.get(c);
            int qty = Integer.parseInt(qtyF.getText());
            double total = priceMap.get(p) * qty;

            // 1. Check if we have enough stock
            PreparedStatement chk = con.prepareStatement("SELECT stock FROM products WHERE id=?");
            chk.setInt(1, pid);
            ResultSet rs = chk.executeQuery();
            if (rs.next() && rs.getInt("stock") < qty) {
                JOptionPane.showMessageDialog(this, "Error: Not enough stock available!");
                return;
            }

            // 2. Record the sale
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO sales (product_id, customer_id, quantity, total) VALUES (?,?,?,?)");
            ps.setInt(1, pid); ps.setInt(2, cid);
            ps.setInt(3, qty); ps.setDouble(4, total);
            ps.executeUpdate();

            // 3. REDUCE THE STOCK automatically
            PreparedStatement upd = con.prepareStatement(
                "UPDATE products SET stock = stock - ? WHERE id=?");
            upd.setInt(1, qty); upd.setInt(2, pid);
            upd.executeUpdate();

            JOptionPane.showMessageDialog(this, "Sale Recorded successfully!\nTotal Bill: ₹" + total);
            qtyF.setText(""); totalF.setText("");
            loadSalesHistory(); 
            loadCombos(); // Refresh products (in case a product went out of stock)
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void loadSalesHistory() {
        model.setRowCount(0);
        String sql = "SELECT s.id, p.name pname, c.name cname, s.quantity, s.total, s.sale_date " +
                     "FROM sales s JOIN products p ON s.product_id=p.id " +
                     "JOIN customers c ON s.customer_id=c.id ORDER BY s.id DESC";
        try (Connection con = DBConnection.getConnection();
             ResultSet rs = con.createStatement().executeQuery(sql)) {
            while (rs.next())
                model.addRow(new Object[]{
                    rs.getInt("id"), rs.getString("pname"), rs.getString("cname"),
                    rs.getInt("quantity"), rs.getDouble("total"), rs.getTimestamp("sale_date")
                });
        } catch (Exception ignored) {}
    }
}