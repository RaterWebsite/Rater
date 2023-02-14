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
