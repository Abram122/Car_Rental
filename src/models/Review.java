package models;

public class Review {
    private int reviewID;
    private int userID;
    private int bookingID;
    private String review;
    private int rating;
    private String username;

    public Review() {
    }

    public Review(int reviewID, int userID, int bookingID, String review, int rating) {
        this.reviewID = reviewID;
        this.userID = userID;
        this.bookingID = bookingID;
        this.review = review;
        this.rating = rating;
    }

    public int getReviewID() {
        return reviewID;
    }

    public void setReviewID(int reviewID) {
        this.reviewID = reviewID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getBookingID() {
        return bookingID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}