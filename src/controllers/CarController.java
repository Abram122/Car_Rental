package controllers;

import dao.CarDAO;
import models.Car;

import java.time.LocalDateTime;
import java.util.List;

public class CarController {
    private final CarDAO carDAO;

    public CarController() {
        this.carDAO = new CarDAO();
    }

    // Add a new car
    public boolean addCar(int modelId, int categoryId, String plateNo, String imageURL, boolean availability,
                          float mileage, float rentalPrice) {
        Car car = new Car();
        car.setModelID(modelId);
        car.setCategoryID(categoryId);
        car.setPlateNo(plateNo);
        car.setImageURL(imageURL);
        car.setAvailabilityStatus(availability ? "Available" : "Unavailable");
        car.setMileage((int) mileage);
        car.setRentalPrice(rentalPrice);
        car.setCreatedAt(LocalDateTime.now());
        car.setUpdatedAt(LocalDateTime.now());

        return carDAO.insertCar(car);
    }

    // Update an existing car
    public boolean updateCar(int carId, int modelId, int categoryId, String plateNo, String imageURL,
                             boolean availability, float mileage, float rentalPrice) {
        Car car = new Car();
        car.setCarID(carId);
        car.setModelID(modelId);
        car.setCategoryID(categoryId);
        car.setPlateNo(plateNo);
        car.setImageURL(imageURL);
        car.setAvailabilityStatus(availability ? "Available" : "Unavailable");
        car.setMileage((int) mileage);
        car.setRentalPrice(rentalPrice);
        car.setUpdatedAt(LocalDateTime.now());

        return carDAO.updateCar(car);
    }

    // Delete a car by ID
    public boolean deleteCar(int carId) {
        return carDAO.deleteCar(carId);
    }

    // Retrieve a car by ID
    public Car getCarById(int carId) {
        return carDAO.getCarById(carId);
    }

    // Retrieve all cars
    public List<Car> getAllCars() {
        return carDAO.getAllCars();
    }

    public boolean addCar(int carModelId, int categoryId, int int1, String string, float float1, String text,
            String text2, String text3) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addCar'");
    }
}