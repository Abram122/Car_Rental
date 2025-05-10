package dao;

import models.Customer;
import models.RentalHistory;
import utils.HashUtil;
import utils.MySQLConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {
    private Connection conn;

    public CustomerDAO() {
        this.conn = MySQLConnection.getInstance().getConnection();
    }

    public boolean register(int userId, String username, String password, String email, String phone,
            String licenseNumber) {
        String sql = "INSERT INTO user (user_id, username, password_hash, email, phone, license_number) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setString(2, username);
            pstmt.setString(3, password);
            pstmt.setString(4, email);
            pstmt.setString(5, phone);
            pstmt.setString(6, licenseNumber);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean login(String email, String password) {
        String sql = "SELECT password_hash FROM user WHERE email = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String storedHash = rs.getString("password_hash");
                return HashUtil.verifyPassword(password, storedHash);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(String email) {
        String sql = "DELETE FROM user WHERE email = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isVerified(String email) {
        String sql = "SELECT is_verified FROM user WHERE email = ?";
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

    public boolean verifyUser(String email) {
        String sql = "UPDATE user SET is_verified = TRUE WHERE email = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

  
    public Customer getByEmail(String email) {
        String sql = "SELECT user_id, username, email, phone, license_number FROM user WHERE email = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Customer(
                    rs.getInt("user_id"),
                    rs.getString("username"),
                    null,
                    rs.getString("phone"),
                    rs.getString("license_number")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateProfile(Customer c) {
        String sql = "UPDATE user SET phone = ?, license_number = ? WHERE user_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, c.getPhone());
            stmt.setString(2, c.getLicenseNumber());
            stmt.setInt(3, c.getUserId());
            return stmt.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<RentalHistory> getRentalHistory(int userId) {
        List<RentalHistory> list = new ArrayList<>();
        String sql = "SELECT rental_id, actual_return_date, comments, created_at, updated_at " +
                     "FROM Rental_History WHERE user_id = ? ORDER BY created_at DESC";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                RentalHistory rh = new RentalHistory(
                    rs.getInt("rental_id"),
                    rs.getTimestamp("actual_return_date").toLocalDateTime(),
                    rs.getString("comments")
                );
                rh.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                rh.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
                list.add(rh);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
