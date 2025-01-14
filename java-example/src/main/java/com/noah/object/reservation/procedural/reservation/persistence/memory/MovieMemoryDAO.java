package com.noah.object.reservation.procedural.reservation.persistence.memory;

import com.noah.object.reservation.procedural.reservation.domain.Movie;
import com.noah.object.reservation.procedural.reservation.persistence.MovieDAO;

public class MovieMemoryDAO extends InMemoryDAO<Movie> implements MovieDAO {
    @Override
    public Movie selectMovie(Long movieId) {
        return findOne(movie -> movie.getId().equals(movieId));
    }
}
