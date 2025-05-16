package controllers;

import dao.ReviewDAO;
import models.Review;
import java.util.List;

public class ReviewController {
    private final ReviewDAO reviewDAO;

    public ReviewController() {
        this.reviewDAO = new ReviewDAO();
    }

    public boolean addReview(Review review) {
        return reviewDAO.addReview(review);
    }

    public Review getReviewById(int id) {
        return reviewDAO.getReviewById(id);
    }

    public List<Review> getAllReviews() {
        return reviewDAO.getAllReviews();
    }

    public Review getReviewByBookingId(int bookingId) {
        return reviewDAO.getReviewByBookingId(bookingId);
    }

    public List<Review> getAllReviewsWithUsername() {
        return reviewDAO.getAllReviewsWithUsername();
    }

    public boolean updateReview(Review review) {
        return reviewDAO.updateReview(review);
    }

    public boolean deleteReview(int id) {
        return reviewDAO.deleteReview(id);
    }
}