package rater.application.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import rater.application.helper.Indices;
import rater.application.model.Movie;
import rater.application.search.SearchRequestDTO;
import rater.application.search.util.SearchUtil;

@Service
public class MovieService {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final Logger LOG = LoggerFactory.getLogger(MovieService.class);

    private final RestHighLevelClient client;

    @Autowired
    public MovieService(RestHighLevelClient client) {
        this.client = client;
    }

    public List<Movie> search(final SearchRequestDTO dto) {
        final SearchRequest request = SearchUtil.buildSearchRequest(Indices.MOVIE_INDEX, dto);

        return searchInternal(request);
    }

    private List<Movie> searchInternal(final SearchRequest request) {
        if (request == null) {
            LOG.error("Failed to build search request");
            return Collections.emptyList();
        }

        try {
            final SearchResponse response = client.search(request, RequestOptions.DEFAULT);

            final SearchHit[] searchHits = response.getHits().getHits();
            final List<Movie> movies = new ArrayList<>(searchHits.length);
            for (SearchHit hit : searchHits) {
                movies.add(MAPPER.readValue(hit.getSourceAsString(), Movie.class));
            }

            return movies;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    public Boolean index(final Movie movie) {
        try {
            final String movieAsString = MAPPER.writeValueAsString(movie);

            final IndexRequest request = new IndexRequest(Indices.MOVIE_INDEX);
            request.id(movie.getId());
            request.source(movieAsString, XContentType.JSON);

            final IndexResponse response = client.index(request, RequestOptions.DEFAULT);

            return response != null && response.status().equals(RestStatus.OK);
        } catch (final Exception e) {
            LOG.error(e.getMessage(), e);
            return false;
        }
    }

    public Movie getById(final String vehicleId) {
        try {
            final GetResponse documentFields = client.get(
                    new GetRequest(Indices.MOVIE_INDEX, vehicleId),
                    RequestOptions.DEFAULT
            );
            if (documentFields == null || documentFields.isSourceEmpty()) {
                return null;
            }

            return MAPPER.readValue(documentFields.getSourceAsString(), Movie.class);
        } catch (final Exception e) {
            LOG.error(e.getMessage(), e);
            return null;
        }
    }
}
