package dao;

import models.Booking;
import utils.MySQLConnection;

import java.sql.*;
import java.util.Date;

public class BookingDAO {
    private final Connection conn;

    public BookingDAO() {
        this.conn = MySQLConnection.getInstance().getConnection();
    }

    public boolean addBooking(Booking booking, int userId, int carId) {
        String sql = "INSERT INTO Booking (user_id, car_id, status, start_date, end_date) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, carId);
            stmt.setString(3, booking.getStatus());
            stmt.setDate(4, new java.sql.Date(booking.getStartDate().getTime()));
            stmt.setDate(5, new java.sql.Date(booking.getEndDate().getTime()));
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Booking getBookingById(int bookingId) {
        String sql = "SELECT * FROM Booking WHERE booking_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, bookingId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Booking(
                    rs.getInt("booking_id"),
                    rs.getDate("start_date"),
                    rs.getDate("end_date"),
                    rs.getString("status")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
