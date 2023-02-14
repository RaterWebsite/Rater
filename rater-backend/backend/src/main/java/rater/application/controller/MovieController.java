package rater.application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rater.application.model.Movie;
import rater.application.search.SearchRequestDTO;
import rater.application.service.MovieService;
import rater.application.service.helper.MovieDummyDataService;

@RestController
@RequestMapping("/api/movie")
public class MovieController {
    private final MovieService service;
    private final MovieDummyDataService dummyDataService;

    @Autowired
    public MovieController(MovieService service, MovieDummyDataService dummyDataService) {
        this.service = service;
        this.dummyDataService = dummyDataService;
    }

    @PostMapping
    public void index(@RequestBody final Movie movie) {
        service.index(movie);
    }

    @PostMapping ("/insertDummyData")
    public void insertDummyData() {
        dummyDataService.insertDummyData();
    }

    @GetMapping("/{id}")
    public Movie getById(@PathVariable final String id) {
        return service.getById(id);
    }

    @PostMapping("/search")
    public List<Movie> search(@RequestBody final SearchRequestDTO dto) {
        return service.search(dto);
    }

    


    
}
