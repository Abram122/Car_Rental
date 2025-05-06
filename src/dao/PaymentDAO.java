package dao;

import models.Payment;
import utils.MySQLConnection;

import java.sql.*;

public class PaymentDAO {
    private final Connection conn;

    public PaymentDAO() {
        this.conn = MySQLConnection.getInstance().getConnection();
    }

    public boolean addPayment(Payment payment, int bookingId) {
        String sql = "INSERT INTO Payment (booking_id, amount, payment_status, payment_method, payment_date) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, bookingId);
            stmt.setDouble(2, payment.getPrice());
            stmt.setString(3, payment.getPaymentStatus());
            stmt.setString(4, payment.getPaymentMethod());
            stmt.setDate(5, new java.sql.Date(payment.getPaymentDate().getTime()));
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Payment getPaymentById(int paymentId) {
        String sql = "SELECT * FROM Payment WHERE payment_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, paymentId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Payment(
                    rs.getInt("payment_id"),
                    rs.getString("payment_status"),
                    rs.getString("payment_method"),
                    rs.getDouble("amount")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
