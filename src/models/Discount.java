// filepath: c:\Users\Zodem\OneDrive\Desktop\OOP Project\Car_Rental\src\models\Discount.java
package models;

public class Discount {
    private int discountId;
    private double discountPercentage;

    public Discount() {}

    public Discount(int discountId, double discountPercentage) {
        this.discountId = discountId;
        this.discountPercentage = discountPercentage;
    }

    public int getDiscountId() {
        return discountId;
    }

    public void setDiscountId(int discountId) {
        this.discountId = discountId;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    
}