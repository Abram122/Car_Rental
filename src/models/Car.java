package models;

import java.time.LocalDateTime;

public class Car {
    private int carID, year, rentedDays, categoryID;
    private String brand, model, registration, imageURL;
    private boolean availability;
    private float mileAge, rentalPrice;
    private LocalDateTime createdAt, updatedAt;

    public Car() {
    }

    public Car(int carID, String brand, String model, int year, int rentedDays, int categoryID, float mileAge,
               boolean availability, float rentalPrice, String registration, String imageURL,
               LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.carID = carID;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.rentedDays = rentedDays;
        this.categoryID = categoryID;
        this.mileAge = mileAge;
        this.availability = availability;
        this.rentalPrice = rentalPrice;
        this.registration = registration;
        this.imageURL = imageURL;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getCarID() {
        return carID;
    }

    public void setCarID(int carID) {
        this.carID = carID;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getRentedDays() {
        return rentedDays;
    }

    public void setRentedDays(int rentedDays) {
        this.rentedDays = rentedDays;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public float getMileAge() {
        return mileAge;
    }

    public void setMileAge(float mileAge) {
        this.mileAge = mileAge;
    }

    public boolean getAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public float getRentalPrice() {
        return rentalPrice;
    }

    public void setRentalPrice(float rentalPrice) {
        this.rentalPrice = rentalPrice;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
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

    public boolean isAvailability(boolean available) {
        return available;
    }
}
