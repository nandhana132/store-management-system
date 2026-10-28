package com.store;

public class Main {
    public static void main(String[] args) {
        try {
            DBConnection.getConnection().close();
            System.out.println("Database connected successfully!");
        } catch (Exception e) {
            System.err.println("DB connection failed: " + e.getMessage());
        }
        new LoginFrame();
    }
}