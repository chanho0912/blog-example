package com.noah.object.reservation.procedural.reservation.persistence;

import com.noah.object.reservation.procedural.reservation.domain.Reservation;

public interface ReservationDAO {
    void insert(Reservation reservation);
}
