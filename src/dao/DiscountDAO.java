package dao;

import models.Discount;
import utils.MySQLConnection;

import java.sql.*;

public class DiscountDAO {
    private final Connection conn;

    public DiscountDAO() {
        this.conn = MySQLConnection.getInstance().getConnection();
    }

    public boolean addDiscount(Discount discount) {
        String sql = "INSERT INTO Discount (discount_percent) VALUES (?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, discount.getDiscountPrecantage());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Discount getDiscountById(int discountId) {
        String sql = "SELECT * FROM Discount WHERE discount_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, discountId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Discount(
                    rs.getInt("discount_id"),
                    rs.getDouble("discount_percent")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
