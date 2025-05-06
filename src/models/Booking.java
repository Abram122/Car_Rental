package models;

import java.util.Date;

public class Booking {
    private int booking_id;
    private Date start_date;
    private Date end_date;
    private String status;
    private Date createdAt;
    private Date updatedAt;

    public Booking(int booking_id, Date start_date, Date end_date, String status) {
        this.booking_id = booking_id;
        this.start_date = start_date;
        this.end_date = end_date;
        this.status = status;
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }
    public int getBookingId() {
        return booking_id;
    }
    public Date getStartDate() {
        return start_date;
    }
    public Date getEndDate() {
        return end_date;
    }
    public String getStatus() {
        return status;
    }
    public Date getCreatedAt() {
        return createdAt;
    }
    public Date getUpdatedAt() {
        return updatedAt;
    }
}
