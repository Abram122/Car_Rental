package dao;

import utils.MySQLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CustomerDAO {

    private Connection conn;

    public CustomerDAO() {
        this.conn = MySQLConnection.getInstance().getConnection();
    }

    public boolean register(int userId, String username, String password, String phone, String licenseNumber) {
        String sql = "INSERT INTO customers (userId, username, password, phone, license_number) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setString(2, username);
            pstmt.setString(3, password);
            pstmt.setString(4, phone);
            pstmt.setString(5, licenseNumber);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
