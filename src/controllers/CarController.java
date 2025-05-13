package controllers;

import dao.CarDAO;
import dao.CarModelDAO;
import dao.CategoryDAO;
import models.Car;
import models.CarModel;
import models.Category;
import utils.ValidationException;
import utils.ValidationUtil;

import java.time.LocalDateTime;
import java.util.List;

public class CarController {
    private final CarDAO carDAO;
    private final CarModelDAO carModelDAO;
    private final CategoryDAO categoryDAO;

    public CarController() {
        this.carDAO = new CarDAO();
        this.carModelDAO = new CarModelDAO();
        this.categoryDAO = new CategoryDAO();
    }    // Add a new car
    public boolean addCar(int modelId, int categoryId, String plateNo, String imageURL, boolean availability,
                          float mileage, float rentalPrice, String fuelType) throws ValidationException {
        // Validate inputs
        ValidationUtil.isValidURL(imageURL);
        
        // Check if model and category exist
        CarModel carModel = carModelDAO.getCarModelById(modelId);
        if (carModel == null) {
            throw new ValidationException("Invalid car model ID");
        }
        
        Category category = categoryDAO.getCategoryById(categoryId);
        if (category == null) {
            throw new ValidationException("Invalid category ID");
        }
        
        Car car = new Car();
        car.setModelID(modelId);
        car.setCategoryID(categoryId);
        car.setPlateNo(plateNo);
        car.setImageURL(imageURL);
        car.setAvailabilityStatus(availability ? "Available" : "Unavailable");
        car.setMileage((int) mileage);
        car.setRentalPrice(rentalPrice);
        car.setFuelType(fuelType);
        car.setCreatedAt(LocalDateTime.now());
        car.setUpdatedAt(LocalDateTime.now());
        
        // Set the model and category objects for reference
        car.setCarModel(carModel);
        car.setCategory(category);

        return carDAO.insertCar(car);
    }    // Update an existing car
    public boolean updateCar(int carId, int modelId, int categoryId, String plateNo, String imageURL,
                             boolean availability, float mileage, float rentalPrice, String fuelType) throws ValidationException {
        // Validate inputs
        ValidationUtil.isValidURL(imageURL);
        
        // Check if model and category exist
        CarModel carModel = carModelDAO.getCarModelById(modelId);
        if (carModel == null) {
            throw new ValidationException("Invalid car model ID");
        }
        
        Category category = categoryDAO.getCategoryById(categoryId);
        if (category == null) {
            throw new ValidationException("Invalid category ID");
        }
        
        Car car = new Car();
        car.setCarID(carId);
        car.setModelID(modelId);
        car.setCategoryID(categoryId);
        car.setPlateNo(plateNo);
        car.setImageURL(imageURL);
        car.setAvailabilityStatus(availability ? "Available" : "Unavailable");
        car.setMileage((int) mileage);
        car.setRentalPrice(rentalPrice);
        car.setFuelType(fuelType);
        car.setUpdatedAt(LocalDateTime.now());
        
        // Set the model and category objects for reference
        car.setCarModel(carModel);
        car.setCategory(category);

        return carDAO.updateCar(car);
    }

    // Delete a car by ID
    public boolean deleteCar(int carId) {
        return carDAO.deleteCar(carId);
    }    // Retrieve a car by ID
    public Car getCarById(int carId) {
        Car car = carDAO.getCarById(carId);
        return car; // CarDAO now handles loading the model and category data
    }

    // Retrieve all cars
    public List<Car> getAllCars() {
        return carDAO.getAllCars(); // CarDAO now handles loading the model and category data
    }
    
    // Get car info formatted for display
    public String getCarDisplayInfo(int carId) {
        Car car = getCarById(carId);
        if (car == null) {
            return "Car not found";
        }
        
        StringBuilder info = new StringBuilder();
        info.append("Car ID: ").append(car.getCarID()).append("\n");
        info.append("Model: ").append(car.getFullModelName()).append("\n");
        info.append("Category: ").append(car.getCategoryName()).append("\n");
        info.append("Plate No: ").append(car.getPlateNo()).append("\n");
        info.append("Status: ").append(car.getAvailabilityStatus()).append("\n");
        info.append("Rental Price: $").append(car.getRentalPrice()).append("/day\n");
        info.append("Mileage: ").append(car.getMileage()).append(" km\n");
        info.append("Fuel Type: ").append(car.getFuelType()).append("\n");
        
        return info.toString();
    }    public boolean addCar(int carModelId, int categoryId, int mileage, String fuelType, float rentalPrice, String plateNo,
            String imageURL, String availabilityStatus) throws ValidationException {
        // This is the implementation for the second addCar method
        // Convert the string availability to boolean
        boolean availability = "Available".equalsIgnoreCase(availabilityStatus);
        
        // Call the main addCar method
        return addCar(carModelId, categoryId, plateNo, imageURL, availability, mileage, rentalPrice, fuelType);
    }
}