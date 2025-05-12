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
    public boolean addDiscount(String promotionCode, int discountPercentage) throws ValidationException {
        // ValidationUtil.isValidPromotionCode(promotionCode); // Validate promotion code
        int discountId = (int) (System.currentTimeMillis() % Integer.MAX_VALUE);
        Discount discount = new Discount();
        discount.setDiscountId(discountId);
        discount.setPromotionCode(promotionCode);
        discount.setDiscountPercentage(discountPercentage);
        return discountDAO.addDiscount(discount);
    }

    // Read a discount by ID
    public Discount getDiscountById(int discountId) {
        return discountDAO.getDiscountById(discountId);
    }

    // Update an existing discount
    public boolean updateDiscount(int discountId, String promotionCode, int discountPercentage) throws ValidationException {
        // ValidationUtil.isValidPromotionCode(promotionCode); // Validate promotion code

        Discount discount = new Discount();
        discount.setDiscountId(discountId);
        discount.setPromotionCode(promotionCode);
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