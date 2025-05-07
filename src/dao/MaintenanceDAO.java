package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import models.Maintenance;
import utils.MySQLConnection;

public class MaintenanceDAO {
    private Connection conn;

    public MaintenanceDAO() {
        this.conn = MySQLConnection.getInstance().getConnection();
    }

    public boolean addMaintenance(Maintenance maintenance) {
        String sql = "INSERT INTO maintenance (status, details, cost, maintenance_date, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maintenance.getStatus());
            stmt.setString(2, maintenance.getDetails());
            stmt.setFloat(3, maintenance.getCost());
            stmt.setTimestamp(4, Timestamp.valueOf(maintenance.getMaintenanceDate()));
            stmt.setTimestamp(5, Timestamp.valueOf(maintenance.getCreatedAt()));
            stmt.setTimestamp(6, Timestamp.valueOf(maintenance.getUpdatedAt()));
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateMaintenance(Maintenance maintenance) {
        String sql = "UPDATE maintenance SET status = ?, details = ?, cost = ?, updated_at = ? WHERE maintenance_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maintenance.getStatus());
            stmt.setString(2, maintenance.getDetails());
            stmt.setFloat(3, maintenance.getCost());
            stmt.setTimestamp(4, Timestamp.valueOf(maintenance.getUpdatedAt()));
            stmt.setInt(5, maintenance.getMaintenanceID());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Maintenance getMaintenanceById(int id) {
        String sql = "SELECT * FROM maintenance WHERE maintenance_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Maintenance maintenance = new Maintenance();
                    maintenance.setMaintenanceID(rs.getInt("maintenance_id"));
                    maintenance.setStatus(rs.getString("status"));
                    maintenance.setDetails(rs.getString("details"));
                    maintenance.setCost(rs.getFloat("cost"));
                    maintenance.setMaintenanceDate(rs.getTimestamp("maintenance_date").toLocalDateTime());
                    maintenance.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                    maintenance.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
                    return maintenance;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Maintenance> getAllMaintenance() {
        List<Maintenance> list = new ArrayList<>();
        String sql = "SELECT * FROM maintenance ORDER BY maintenance_date DESC";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Maintenance maintenance = new Maintenance();
                maintenance.setMaintenanceID(rs.getInt("maintenance_id"));
                maintenance.setStatus(rs.getString("status"));
                maintenance.setDetails(rs.getString("details"));
                maintenance.setCost(rs.getFloat("cost"));
                maintenance.setMaintenanceDate(rs.getTimestamp("maintenance_date").toLocalDateTime());
                maintenance.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                maintenance.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
                list.add(maintenance);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean deleteMaintenance(int id) {
        String sql = "DELETE FROM maintenance WHERE maintenance_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
