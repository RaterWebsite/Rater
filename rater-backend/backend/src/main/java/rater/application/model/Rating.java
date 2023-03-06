package rater.application.model;

public class Rating {

    private String movieName;
    private float score;
    private String review;


    
    public Rating(String movieName, float score, String review) {
        this.movieName = movieName;
        this.score = score;
        this.review = review;
    }

    public String getMovieName() {
        return movieName;
    }
    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }
    public float getScore() {
        return score;
    }
    public void setScore(float score) {
        this.score = score;
    }
    public String getReview() {
        return review;
    }
    public void setReview(String review) {
        this.review = review;
    }

    
    
}
