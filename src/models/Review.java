package models;

public class Review {
    private int reviewID;
    private String review;

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

    public void submitReview(){
        
    }
}

