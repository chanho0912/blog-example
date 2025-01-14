package com.noah.object.reservation.procedural.reservation.persistence;

import com.noah.object.reservation.procedural.reservation.domain.Movie;

public interface MovieDAO {
    Movie selectMovie(Long movieId);

    void insert(Movie movie);
}
