package dao;

import models.Admin;
import utils.MySQLConnection;
import utils.HashUtil;
import utils.ValidationException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public boolean register(int admin_id, String username, String password, String email, String role) throws ValidationException {
        String sql = "INSERT INTO admin (admin_id, username, password_hash, email, admin_role) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, admin_id);
            stmt.setString(2, username);
            stmt.setString(3, password);
            stmt.setString(4, email);
            stmt.setString(5, role);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; 
        } catch (SQLException e) {
            if (e.getMessage().contains("Duplicate entry")) {
                throw new ValidationException("Error: Duplicate entry for username or email.");
            }
            e.printStackTrace();
        }
        return false;
    }
    
    public List<Admin> getAllAdmins() {
        List<Admin> admins = new ArrayList<>();
        String sql = "SELECT admin_id, username, email, admin_role FROM admin";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Admin admin = new Admin(
                    rs.getInt("admin_id"),
                    rs.getString("username"),
                    null, // We don't retrieve passwords
                    null, // Phone is not stored for admins
                    rs.getString("admin_role")
                );
                admins.add(admin);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return admins;
    }
    
    public Admin getAdminByEmail(String email) {
        String sql = "SELECT admin_id, username, admin_role FROM admin WHERE email = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Admin(
                    rs.getInt("admin_id"),
                    rs.getString("username"),
                    null, // We don't retrieve passwords
                    null, // Phone is not stored for admins
                    rs.getString("admin_role")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public boolean deleteAdmin(int adminId) {
        String sql = "DELETE FROM admin WHERE admin_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, adminId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean isSuperAdmin(String email) {
        String sql = "SELECT admin_role FROM admin WHERE email = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return "SUPER_ADMIN".equals(rs.getString("admin_role"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
