package com.store;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class CustomerPanel extends JPanel {
    JTable table;
    DefaultTableModel model;

    public CustomerPanel() {
        setLayout(new BorderLayout());

        // Form
        JPanel form = new JPanel(new GridLayout(1, 4, 5, 5));
        JTextField nameF = new JTextField();
        JTextField phoneF = new JTextField();
        JButton addBtn = new JButton("Add Customer");
        
        form.add(new JLabel("Name:"));
        form.add(nameF);
        form.add(new JLabel("Phone:"));
        form.add(phoneF);
        form.add(addBtn);
        
        add(form, BorderLayout.NORTH);

        // Table
        model = new DefaultTableModel(new String[]{"ID", "Name", "Phone"}, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        addBtn.addActionListener(e -> {
            try (Connection con = DBConnection.getConnection()) {
                PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO customers (name, phone) VALUES (?,?)");
                ps.setString(1, nameF.getText());
                ps.setString(2, phoneF.getText());
                ps.executeUpdate();
                nameF.setText(""); phoneF.setText("");
                load();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });
        load();
    }

    private void load() {
        model.setRowCount(0);
        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM customers")) {
            while (rs.next())
                model.addRow(new Object[]{rs.getInt("id"), rs.getString("name"), rs.getString("phone")});
        } catch (Exception ignored) {}
    }
}