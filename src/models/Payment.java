package models;

import java.util.Date;

public class Payment {
    private int payment_id;
    private String payment_status;
    private String payment_method;
    private double price;
    private Date payment_date;
    private Date createdAt;
    private Date updatedAt;

    public Payment(int payment_id, String payment_status, String payment_method, double price) {
        this.payment_id = payment_id;
        this.payment_status = payment_status;
        this.payment_method = payment_method;
        this.price = price;
        this.payment_date = new Date();
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    public int getPaymentId() {
        return payment_id;
    }
    public String getPaymentStatus() {
        return payment_status;
    }
    public String getPaymentMethod() {
        return payment_method;
    }
    public double getPrice() {
        return price;
    }
    public Date getPaymentDate() {
        return payment_date;
    }
    public Date getCreatedAt() {
        return createdAt;
    }
    public Date getUpdatedAt() {
        return updatedAt;
    }
}
