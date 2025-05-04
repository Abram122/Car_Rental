package dao;

import utils.MySQLConnection;
import utils.HashUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

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

    
    public String getEmailByUsername(String username) {
        String sql = "SELECT email FROM admin WHERE username = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("email");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

   
    public boolean isOtpEnabled(String username) {
        String sql = "SELECT otp_enabled FROM admin WHERE username = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getBoolean("otp_enabled");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    
    public void setOtpEnabled(String username, boolean enabled) {
        String sql = "UPDATE admin SET otp_enabled = ? WHERE username = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBoolean(1, enabled);
            stmt.setString(2, username);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    public void saveOtp(String username, String otp, Timestamp expiry) {
        String sql = "UPDATE admin SET otp_code = ?, otp_expiry = ? WHERE username = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, otp);
            stmt.setTimestamp(2, expiry);
            stmt.setString(3, username);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

   
    public boolean verifyOtp(String username, String code) {
        String sql = "SELECT otp_code, otp_expiry FROM admin WHERE username = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String stored = rs.getString("otp_code");
                    Timestamp expiry = rs.getTimestamp("otp_expiry");
                    return stored != null
                        && stored.equals(code)
                        && expiry != null
                        && expiry.after(new Timestamp(System.currentTimeMillis()));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
