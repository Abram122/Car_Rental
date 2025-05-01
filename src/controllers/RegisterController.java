package controllers;
import utils.HashUtil;

import dao.CustomerDAO;
import utils.ValidationException;

public class RegisterController {
    private CustomerDAO customerDAO;

    public RegisterController() {
        this.customerDAO = new CustomerDAO();
    }

    // Method to handle customer registration
    public boolean register(String username, String password, String email ,  String phone, String licenseNumber) throws ValidationException {
        // Validate input just simple validation for empty fields
        if (username == null || username.isEmpty() ||
            password == null || password.isEmpty() ||
            phone == null || phone.isEmpty() ||
            licenseNumber == null || licenseNumber.isEmpty()) {
            throw new ValidationException("All fields are required! Please fill in all fields.");
        }

        
int userId = (int) (System.currentTimeMillis() % Integer.MAX_VALUE);

String hashed = HashUtil.hashPassword(password);

//pass the hash (not plain text) to the DAO
return customerDAO.register(userId, username, hashed, email, phone, licenseNumber);

    }
}