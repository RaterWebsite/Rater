package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import models.Rating;
import repositories.RatingRepository;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("api/")
public class RatingController {

    @Autowired
    private RatingRepository ratingRepository;

    @GetMapping("ratings")
    public List<Rating> getRatings() {
        return this.ratingRepository.findAll();
    }
    
}