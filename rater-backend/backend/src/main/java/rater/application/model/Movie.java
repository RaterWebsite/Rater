package rater.application.model;

import java.util.List;
import java.util.Map;

public class Movie {

    private String id;
    private String title;
    private List<String> starring;
    private int runtime; //minutes
    private List<String> genre;
    private String description;
    private int releaseYear;
    private String rating; //G, PG, etc.
    private String image;
    private Map<String, Integer> categories;
    
    public List<String> getStarring() {
        return starring;
    }
    public void setStarring(List<String> starring) {
        this.starring = starring;
    }
    public int getRuntime() {
        return runtime;
    }
    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }
    public List<String> getGenre() {
        return genre;
    }
    public void setGenre(List<String> genre) {
        this.genre = genre;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public int getReleaseYear() {
        return releaseYear;
    }
    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }
    public String getRating() {
        return rating;
    }
    public void setRating(String rating) {
        this.rating = rating;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public Map<String, Integer> getCategories() {
        return categories;
    }
    public void setCategories(Map<String, Integer> categories) {
        this.categories = categories;
    }
    public String getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public void setId(String id) {
        this.id = id;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    
    
}