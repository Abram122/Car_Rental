package controllers;

import dao.PaymentDAO;
import models.Payment;

import java.util.List;

public class PaymentController {
    private final PaymentDAO paymentDAO;

    public PaymentController() {
        this.paymentDAO = new PaymentDAO();
    }

    // Add a new payment
    public boolean addPayment(Payment payment) {
        return paymentDAO.addPayment(payment, payment.getBookingId());
    }

    // Get a payment by ID
    public Payment getPaymentById(int paymentId) {
        return paymentDAO.getPaymentById(paymentId);
    }

    // Update a payment
    public boolean updatePayment(Payment payment) {
        return paymentDAO.updatePayment(payment);
    }

    // Delete a payment
    public boolean deletePayment(Payment payment) {
        return paymentDAO.deletePayment(payment.getPaymentId());
    }

    public List<Payment> getPaymentsByBookingId(int bookingId) {
    return paymentDAO.getPaymentsByBookingId(bookingId);
}

    // Get all payments
    public List<Payment> getAllPayments() {
        return paymentDAO.getAllPayments();
    }
}