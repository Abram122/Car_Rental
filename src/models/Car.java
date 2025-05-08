package models;

import java.time.LocalDateTime;

public class Car {
    private int carID, year, rentedDays, categoryID;
    private String brand, model, registration, imageURL;
    private boolean availability;
    private float mileAge, rentalPrice;
    private LocalDateTime createdAt, updatedAt;

    public boolean getAvailability() {
        return availability;
    }

    public String getBrand() {
        return brand;
    }

    public int getCarID() {
        return carID;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getImageURL() {
        return imageURL;
    }

    public float getMileAge() {
        return mileAge;
    }

    public String getModel() {
        return model;
    }

    public String getRegistration() {
        return registration;
    }

    public float getRentalPrice() {
        return rentalPrice;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public int getYear() {
        return year;
    }

    public int getRentedDays() {
        return rentedDays;
    }

    public void setRentedDays(int rentedDays) {
        this.rentedDays = rentedDays;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setCarID(int carID) {
        this.carID = carID;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setMileAge(float mileAge) {
        this.mileAge = mileAge;
    }

    public void setRentalPrice(float rentalPrice) {
        this.rentalPrice = rentalPrice;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public Car() {
    }

    public Car(int carID, int year, int rentedDays, String brand, String model, String registration, String imageURL,
            boolean availability, float mileAge, float rentalPrice, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.carID = carID;
        this.year = year;
        this.rentedDays = rentedDays;
        this.brand = brand;
        this.model = model;
        this.registration = registration;
        this.imageURL = imageURL;
        this.availability = availability;
        this.mileAge = mileAge;
        this.rentalPrice = rentalPrice;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void updateAvailability() {
        this.availability = false;
        this.updatedAt = LocalDateTime.now();
    }

    public String getDetails() {
        return """
                --- Car Details ---
                Car ID: """ + carID + "\n" +
                "Brand: " + brand + "\n" +
                "Model: " + model + "\n" +
                "Year: " + year + "\n" +
                "Registration: " + registration + "\n" +
                "Availability: " + (availability ? "Yes" : "No") + "\n" +
                "Mile Age: " + mileAge + "\n" +
                "Rental Days: " + rentedDays + "\n" +
                "Rental Price: " + rentalPrice + " $\n" +
                "Image URL: " + imageURL;
    }

    public float calculateRentalPrice() {
        return rentalPrice * rentedDays;
    }

    public boolean isCarAvailable() {
        return availability;
    }

}
