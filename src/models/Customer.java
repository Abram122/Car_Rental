package models;

import java.sql.*;

public class Customer extends User {
    private String license_number;

    //Public Constructor ------------------------> 
    public Customer(int userId, String username, String password, String phone, String license_number) {
        super(userId, username, password, phone);
        this.license_number = license_number;
    }

    //getter and SSetter for license_number because it is private
    public String getLicenseNumber() {
        return license_number;
    }

    public void setLicenseNumber(String license_number) {
        this.license_number = license_number;
    }

    //register method with prepared statement check it abram
    private int register() {
        String DB_URL = "jdbc:mysql://localhost:3306/car_rental";
        String USER = "root";
        String PASS = "your_password";

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String sql = "INSERT INTO customers (userId, username, password, phone, license_number) VALUES (?, ?, ?, ?, ?)";// i donot know if this is the final structure
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, this.userId);
            pstmt.setString(2, this.username);
            pstmt.setString(3, this.password);
            pstmt.setString(4, this.phone);
            pstmt.setString(5, this.license_number);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                return 1; // 
            } else {
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;  
        }
    }

    //Override login method because we are gamden awy we it comes to OOP
    @Override
    protected int login(String username, String password) {
        return super.login(username, password); // بيستخدم الـ login بتاع User
    }

    //Public method to call register (since register is private in UML) i donot know if you want it like this.
    public int performRegistration() {
        return register();
    }
}