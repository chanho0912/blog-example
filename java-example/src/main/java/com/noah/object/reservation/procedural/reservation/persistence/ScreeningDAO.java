package com.noah.object.reservation.procedural.reservation.persistence;

import com.noah.object.reservation.procedural.reservation.domain.Screening;

public interface ScreeningDAO {
    Screening selectScreening(Long screeningId);

    void insert(Screening screening);
}
