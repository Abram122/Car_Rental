package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import models.Car;
import utils.MySQLConnection;

public class CarDAO {
    private Connection conn;

    public CarDAO() {
        this.conn = MySQLConnection.getInstance().getConnection();
    }
    // Insert a new car into the database
    public boolean insertCar(Car car) {
        String sql = "INSERT INTO cars (year, rented_days, brand, model, registration, image_url, availability, mileage, rental_price, created_at, updated_at, category_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {    
            stmt.setInt(1, car.getYear());
            stmt.setInt(2, car.getRentedDays());
            stmt.setString(3, car.getBrand());
            stmt.setString(4, car.getModel());
            stmt.setString(5, car.getRegistration());
            stmt.setString(6, car.getImageURL());
            stmt.setBoolean(7, car.getAvailability());
            stmt.setFloat(8, car.getMileAge());
            stmt.setFloat(9, car.getRentalPrice());
            stmt.setTimestamp(10, Timestamp.valueOf(car.getCreatedAt()));
            stmt.setTimestamp(11, Timestamp.valueOf(car.getUpdatedAt()));
            stmt.setInt(12, car.getCategoryID());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; 
    }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Retrieve a car by ID
    public Car getCarById(int carId) {
        String sql = "SELECT * FROM cars WHERE car_id = ?";
        Car car = null;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, carId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                car = mapResultSetToCar(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return car;
    }

    // Retrieve all cars
    public List<Car> getAllCars() {
        List<Car> carList = new ArrayList<>();
        String sql = "SELECT * FROM cars";

        try (Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                carList.add(mapResultSetToCar(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return carList;
    }

    // Update an existing car
    public boolean updateCar(Car car) {
        String sql = "UPDATE cars SET year=?, rented_days=?, brand=?, model=?, registration=?, image_url=?, availability=?, mileage=?, rental_price=?, updated_at=?, category_id=? WHERE car_id=?";

        try (PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setInt(1, car.getYear());
            stmt.setInt(2, car.getRentedDays());
            stmt.setString(3, car.getBrand());
            stmt.setString(4, car.getModel());
            stmt.setString(5, car.getRegistration());
            stmt.setString(6, car.getImageURL());
            stmt.setBoolean(7, car.getAvailability());
            stmt.setFloat(8, car.getMileAge());
            stmt.setFloat(9, car.getRentalPrice());
            stmt.setTimestamp(10, Timestamp.valueOf(car.getUpdatedAt()));
            stmt.setInt(11, car.getCategoryID());
            stmt.setInt(12, car.getCarID());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete a car by ID
    public boolean deleteCar(int carId) {
        String sql = "DELETE FROM cars WHERE car_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, carId);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Helper method to map result set to Car object
    private Car mapResultSetToCar(ResultSet rs) throws SQLException {
        Car car = new Car();
        car.setCarID(rs.getInt("car_id"));
        car.setYear(rs.getInt("year"));
        car.setRentedDays(rs.getInt("rented_days"));
        car.setBrand(rs.getString("brand"));
        car.setModel(rs.getString("model"));
        car.setRegistration(rs.getString("registration"));
        car.setImageURL(rs.getString("image_url"));
        car.setAvailability(rs.getBoolean("availability"));
        car.setMileAge(rs.getFloat("mileage"));
        car.setRentalPrice(rs.getFloat("rental_price"));
        car.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        car.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        car.setCategoryID(rs.getInt("category_id"));
        return car;
    }
}
