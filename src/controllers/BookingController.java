package controllers;

import dao.BookingDAO;
import models.Booking;

import java.util.List;

public class BookingController {
    private final BookingDAO bookingDAO;

    public BookingController() {
        this.bookingDAO = new BookingDAO();
    }

    // Add a new booking
    public boolean addBooking(int userId, int carId, String status, java.util.Date startDate, java.util.Date endDate) {
        Booking booking = new Booking();
        booking.setUserId(userId);
        booking.setCarId(carId);
        booking.setStatus(status);
        booking.setStartDate(startDate);
        booking.setEndDate(endDate);
        return bookingDAO.addBooking(booking);
    }

    // Retrieve a booking by ID
    public Booking getBookingById(int bookingId) {
        return bookingDAO.getBookingById(bookingId);
    }

    // Retrieve all bookings for a specific user
    public List<Booking> getBookingsByUserId(int userId) {
        return bookingDAO.getBookingsByUserId(userId);
    }

    // Retrieve all bookings
    public List<Booking> getAllBookings() {
        return bookingDAO.getAllBookings();
    }

    // Update an existing booking
    public boolean updateBooking(int bookingId, int userId, int carId, String status, java.util.Date startDate, java.util.Date endDate) {
        Booking booking = new Booking();
        booking.setBookingId(bookingId);
        booking.setUserId(userId);
        booking.setCarId(carId);
        booking.setStatus(status);
        booking.setStartDate(startDate);
        booking.setEndDate(endDate);
        return bookingDAO.updateBooking(booking);
    }

    // Delete a booking by ID
    public boolean deleteBooking(int bookingId) {
        return bookingDAO.deleteBooking(bookingId);
    }
}