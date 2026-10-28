package com.store;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class PurchasePanel extends JPanel {
    JComboBox<String> productBox, supplierBox;
    JTextField qtyF, totalF;
    JTable table;
    DefaultTableModel model;
    
    Map<String, Integer> productMap = new HashMap<>();
    Map<String, Integer> supplierMap = new HashMap<>();
    Map<String, Double> priceMap = new HashMap<>();

    public PurchasePanel() {
        setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(2, 5, 5, 5));
        productBox = new JComboBox<>();
        supplierBox = new JComboBox<>();
        qtyF = new JTextField();
        totalF = new JTextField();
        totalF.setEditable(false);
        
        JButton calcBtn = new JButton("Calculate Cost");
        JButton buyBtn = new JButton("Record Purchase");

        form.add(new JLabel("Select Product:")); form.add(new JLabel("Select Supplier:"));
        form.add(new JLabel("Quantity:")); form.add(new JLabel("Total Cost (₹):")); form.add(new JLabel(""));
        
        form.add(productBox); form.add(supplierBox);
        form.add(qtyF); form.add(totalF); form.add(calcBtn);
        
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(form, BorderLayout.NORTH);
        
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.add(buyBtn);
        topPanel.add(btnPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);

        model = new DefaultTableModel(new String[]{"ID", "Product", "Supplier", "Quantity", "Total Cost", "Date & Time"}, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        calcBtn.addActionListener(e -> calculateTotal());
        buyBtn.addActionListener(e -> recordPurchase());
        
        loadCombos();
        loadPurchaseHistory();
    }

    private void loadCombos() {
        try (Connection con = DBConnection.getConnection()) {
            // Load ALL products (even if stock is 0, because we are buying more)
            ResultSet rs = con.createStatement().executeQuery("SELECT id, name, price FROM products");
            productBox.removeAllItems(); productMap.clear(); priceMap.clear();
            while (rs.next()) {
                String label = rs.getString("name");
                productBox.addItem(label);
                productMap.put(label, rs.getInt("id"));
                priceMap.put(label, rs.getDouble("price")); // Using selling price as buying cost for simplicity
            }

            // Load Suppliers
            rs = con.createStatement().executeQuery("SELECT id, name FROM suppliers");
            supplierBox.removeAllItems(); supplierMap.clear();
            while (rs.next()) {
                String label = rs.getString("name");
                supplierBox.addItem(label);
                supplierMap.put(label, rs.getInt("id"));
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

    private void recordPurchase() {
        try (Connection con = DBConnection.getConnection()) {
            String p = (String) productBox.getSelectedItem();
            String s = (String) supplierBox.getSelectedItem();
            
            if (p == null || s == null) {
                JOptionPane.showMessageDialog(this, "Please make sure you have products and suppliers added first.");
                return;
            }

            int pid = productMap.get(p);
            int sid = supplierMap.get(s);
            int qty = Integer.parseInt(qtyF.getText());
            double total = priceMap.get(p) * qty;

            // 1. Record the purchase
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO purchases (product_id, supplier_id, quantity, total) VALUES (?,?,?,?)");
            ps.setInt(1, pid); ps.setInt(2, sid);
            ps.setInt(3, qty); ps.setDouble(4, total);
            ps.executeUpdate();

            // 2. INCREASE THE STOCK automatically
            PreparedStatement upd = con.prepareStatement(
                "UPDATE products SET stock = stock + ? WHERE id=?");
            upd.setInt(1, qty); upd.setInt(2, pid);
            upd.executeUpdate();

            JOptionPane.showMessageDialog(this, "Purchase Recorded successfully!\nStock Increased.");
            qtyF.setText(""); totalF.setText("");
            loadPurchaseHistory(); 
            loadCombos(); 
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void loadPurchaseHistory() {
        model.setRowCount(0);
        String sql = "SELECT pu.id, p.name pname, s.name sname, pu.quantity, pu.total, pu.purchase_date " +
                     "FROM purchases pu JOIN products p ON pu.product_id=p.id " +
                     "JOIN suppliers s ON pu.supplier_id=s.id ORDER BY pu.id DESC";
        try (Connection con = DBConnection.getConnection();
             ResultSet rs = con.createStatement().executeQuery(sql)) {
            while (rs.next())
                model.addRow(new Object[]{
                    rs.getInt("id"), rs.getString("pname"), rs.getString("sname"),
                    rs.getInt("quantity"), rs.getDouble("total"), rs.getTimestamp("purchase_date")
                });
        } catch (Exception ignored) {}
    }
}