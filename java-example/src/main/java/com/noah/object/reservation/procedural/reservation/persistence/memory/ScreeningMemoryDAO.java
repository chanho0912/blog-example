package com.noah.object.reservation.procedural.reservation.persistence.memory;

import com.noah.object.reservation.procedural.reservation.domain.Screening;
import com.noah.object.reservation.procedural.reservation.persistence.ScreeningDAO;

public class ScreeningMemoryDAO extends InMemoryDAO<Screening> implements ScreeningDAO {
    @Override
    public Screening selectScreening(Long id) {
        return findOne(screening -> screening.getId().equals(id));
    }

}
