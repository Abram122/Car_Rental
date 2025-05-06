package models;

import java.util.Date;

public class Discount {
    private int discount_id;
    private double discount_precantage;
    private Date createdAt;
    private Date updatedAt;

    public Discount(int discount_id, double discount_precantage) {
        this.discount_id = discount_id;
        this.discount_precantage = discount_precantage;
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    public int getDiscountId() {
        return discount_id;
    }
    public double getDiscountPrecantage() {
        return discount_precantage;
    }
    public Date getCreatedAt() {
        return createdAt;
    }
    public Date getUpdatedAt() {
        return updatedAt;
    }
}
