package models;
import java.time.LocalDateTime;

public class RentalHistory {
    private int rentalId;
    private LocalDateTime actualReturnDate;
    private String comments;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public RentalHistory(int rentalId, LocalDateTime actualReturnDate, String comments) {
        this.rentalId = rentalId;
        this.actualReturnDate = actualReturnDate;
        this.comments = comments;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public int getRentalId() {
        return rentalId;
    }

    public void setRentalId(int rentalId) {
        this.rentalId = rentalId;
    }

    public LocalDateTime getActualReturnDate() {
        return actualReturnDate;
    }

    public void setActualReturnDate(LocalDateTime actualReturnDate) {
        this.actualReturnDate = actualReturnDate;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getRentalDetails() {
        return "Rental ID: " + rentalId + ", Return Date: " + actualReturnDate + ", Comments: " + comments;
    }

}