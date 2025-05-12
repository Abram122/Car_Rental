package controllers;

import dao.DiscountDAO;
import models.Discount;
import utils.ValidationException;
import utils.ValidationUtil;

import java.util.List;

public class DiscountController {
    private final DiscountDAO discountDAO;

    public DiscountController() {
        this.discountDAO = new DiscountDAO();
    }

    // Create a new discount
    public boolean addDiscount(int discountId, double discountPercentage) throws ValidationException {

        Discount discount = new Discount();
        discount.setDiscountId(discountId);
        discount.setDiscountPercentage(discountPercentage);
        return discountDAO.addDiscount(discount);
    }

    // Read a discount by ID
    public Discount getDiscountById(int discountId) {
        return discountDAO.getDiscountById(discountId);
    }

    // Update an existing discount
    public boolean updateDiscount(int discountId, double discountPercentage) throws ValidationException {

        Discount discount = new Discount();
        discount.setDiscountId(discountId);
        discount.setDiscountPercentage(discountPercentage);
        return discountDAO.updateDiscount(discount);
    }

    // Delete a discount by ID
    public boolean deleteDiscount(int discountId) {
        return discountDAO.deleteDiscount(discountId);
    }

    // Get all discounts
    public List<Discount> getAllDiscounts() {
        return discountDAO.getAllDiscounts();
    }
}