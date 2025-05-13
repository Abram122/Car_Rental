package controllers;

import dao.CarDAO;
import models.Car;
import utils.ValidationException;
import utils.ValidationUtil;

import java.time.LocalDateTime;
import java.util.List;

public class CarController {
    private final CarDAO carDAO;

    public CarController() {
        this.carDAO = new CarDAO();
    }

    public boolean addCar(int year, int rentedDays, String brand, String model, String registration,
                          String imageURL, boolean availability, float mileage, float rentalPrice, int categoryId)
            throws ValidationException {

        Car car = new Car();
        car.setYear(year);
        car.setRentedDays(rentedDays);
        car.setBrand(brand);
        car.setModel(model);
        car.setRegistration(registration);
        car.setImageURL(imageURL);
        car.setAvailability(availability);
        car.setMileAge(mileage);
        car.setRentalPrice(rentalPrice);
        car.setCategoryID(categoryId);
        car.setCreatedAt(LocalDateTime.now());
        car.setUpdatedAt(LocalDateTime.now());

        return carDAO.insertCar(car);
    }

    public boolean updateCar(int carId, int year, int rentedDays, String brand, String model, String registration,
                             String imageURL, boolean availability, float mileage, float rentalPrice, int categoryId)
            throws ValidationException {



        Car car = new Car();
        car.setCarID(carId);
        car.setYear(year);
        car.setRentedDays(rentedDays);
        car.setBrand(brand);
        car.setModel(model);
        car.setRegistration(registration);
        car.setImageURL(imageURL);
        car.setAvailability(availability);
        car.setMileAge(mileage);
        car.setRentalPrice(rentalPrice);
        car.setCategoryID(categoryId);
        car.setUpdatedAt(LocalDateTime.now());

        return carDAO.updateCar(car);
    }

    public boolean deleteCar(int carId) {
        return carDAO.deleteCar(carId);
    }

    public Car getCarById(int carId) {
        return carDAO.getCarById(carId);
    }

    public List<Car> getAllCars() {
        return carDAO.getAllCars();
    }
}
