package models;
import java.time.LocalDateTime;

public class Car {
    private int carID, year;
    private String brand, model, registration, imageURL;
    private boolean availability;
    private float mileAge, rentalPrice;
    private LocalDateTime createdAt,updatedAt;

    public boolean getAvailability(){
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
    

    public void updateAvailability (){
        
    }

    public String getDetails(){
        return """
               --- Car Details ---
               Car ID: """ + carID+
               "Brand: " + brand +
               "Model: " +model+
               "Year: " +year+
               "Registration: " +registration+
               "Availability: " +(availability?"Yes":"No")+
               "Mile Age: " +mileAge+
               "Rental Price: " +rentalPrice+" $"+
               "Image URL: " + imageURL;
    }

    public float calculateRentalPrice(){
        

    }

    public boolean isCarAvailable(){
        return availability;
    }



    
}










