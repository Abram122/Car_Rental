package dao;

import utils.MySQLConnection;
import utils.HashUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDAO {
    private Connection conn;

    public AdminDAO() {
        this.conn = MySQLConnection.getInstance().getConnection();
    }

    public boolean login(String username, String password) {
        String sql = "SELECT password_hash FROM admin WHERE email = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String storedHash = rs.getString("password_hash");
                    return HashUtil.verifyPassword(password, storedHash);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean register(int admin_id,String username, String password, String email) {
        String sql = "INSERT INTO admin (admin_id,username, password_hash, email) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, email);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; 
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
