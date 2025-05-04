package dao;

import utils.MySQLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDAO {

    private Connection conn;

    public CustomerDAO() {
        this.conn = MySQLConnection.getInstance().getConnection();
    }

    public boolean register(int userId, String username, String password, String email , String phone, String licenseNumber) {
        String sql = "INSERT INTO user (user_id, username, password_hash, email , phone, license_number) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setString(2, username);
            pstmt.setString(3, password);  

            pstmt.setString(4, email);
            pstmt.setString(5, phone);
            pstmt.setString(6, licenseNumber);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean is_verified(String email) {
        String sql = "Select is_verified FROM user WHERE email = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getBoolean("is_verified");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean verify_user(String email) {
        String sql = "UPDATE user SET is_verified = TRUE WHERE email = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            int rowsAffected = stmt.executeUpdate(); 
            return rowsAffected > 0; 
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
