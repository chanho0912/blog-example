package com.noah.object.reservation.procedural.reservation.persistence.memory;

import com.noah.object.reservation.procedural.reservation.domain.Reservation;
import com.noah.object.reservation.procedural.reservation.persistence.ReservationDAO;

public class ReservationMemoryDAO extends InMemoryDAO<Reservation> implements ReservationDAO {
    @Override
    public void insert(Reservation reservation) {
        super.insert(reservation);
    }
}
