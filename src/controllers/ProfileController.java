package controllers;

import dao.CustomerDAO;
import models.Customer;
import models.RentalHistory;

import java.util.List;

public class ProfileController {
    private CustomerDAO dao = new CustomerDAO();

    public Customer load(String email) {
        return dao.getByEmail(email);
    }

   
    public boolean save(Customer c) {
        return dao.updateProfile(c);
    }

  
    public List<RentalHistory> history(int userId) {
        return dao.getRentalHistory(userId);
    }
}
