package dao;

import utils.MySQLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class RentalHistoryDAO {

    private Connection conn;

    public RentalHistoryDAO() {
        this.conn = MySQLConnection.getInstance().getConnection();
    }

    public boolean log(int rentalId, LocalDateTime actualReturnDate, String comments, LocalDateTime createdAt, LocalDateTime updatedAt) {
        String sql = "INSERT INTO rental_history (rentalId, actualReturnDate, comments, createdAt, updatedAt) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, rentalId);
            pstmt.setTimestamp(2, Timestamp.valueOf(actualReturnDate));
            pstmt.setString(3, comments);
            pstmt.setTimestamp(4, Timestamp.valueOf(createdAt));
            pstmt.setTimestamp(5, Timestamp.valueOf(updatedAt));

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
