package models;

import java.util.ArrayList;

public class Review {
    private int reviewID;
    private String review;

    ArrayList<Review> reviews = new ArrayList<>();

    public void setReview(String review) {
        this.review = review;
    }
    public void setReviewID(int reviewID) {
        this.reviewID = reviewID;
    }
    public String getReview() {
        return review;
    }
    public int getReviewID() {
        return reviewID;
    }

    public Review() {
    }

    public Review(int reviewID, String review) {
        this.reviewID = reviewID;
        this.review = review;
    }

    public void submitReview(){
        reviews.add(this);
        System.out.println("Review Added Successfully!");
    }
}


