package rater.application.service.helper;

import java.io.File;
import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import rater.application.model.Movie;
import rater.application.service.MovieService;

@Service
public class MovieDummyDataService {
    private static final Logger LOG = LoggerFactory.getLogger(MovieDummyDataService.class);
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    private final MovieService movieService;

    public MovieDummyDataService(final MovieService movieService) {
        this.movieService = movieService;
    }

    public void insertDummyData() {
        File jsonDirectory = new File(System.getProperty("user.dir") + "/backend/src/main/resources/static/documents");
        File[] jsonFiles = jsonDirectory.listFiles();
        for (File jsonDoc: jsonFiles) {
            movieService.index(buildMovie(jsonDoc));
        }
    }

    private static Movie buildMovie(File jsonDoc) {
        try {
            LOG.info("The file used is:" + jsonDoc.getPath());
            Movie result = MAPPER.readValue(jsonDoc, Movie.class);
            LOG.info("We succesfully created a movie object from JSON");
            return result;
        } catch (Exception e) {
            LOG.error("Error building movie from JSON document");
            LOG.error(e.getMessage());
            return null;
        }
        
    }
}
