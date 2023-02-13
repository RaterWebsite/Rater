package rater.application.service.helper;

import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import rater.application.model.Movie;
import rater.application.service.MovieService;

@Service
public class MovieDummyDataService {
    private static final Logger LOG = LoggerFactory.getLogger(MovieDummyDataService.class);
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    private final MovieService movieService;

    public MovieDummyDataService(final MovieService movieService) {
        this.movieService = movieService;
    }

    public void insertDummyData() {
        movieService.index(buildMovie("1", "Raiders"));
        movieService.index(buildMovie("2", "Blade Runner 2049"));
    }

    private static Movie buildMovie(final String id, final String title) {
        Movie movie = new Movie();
        movie.setId(id);
        movie.setTitle(title);

        return movie;
    }
}
