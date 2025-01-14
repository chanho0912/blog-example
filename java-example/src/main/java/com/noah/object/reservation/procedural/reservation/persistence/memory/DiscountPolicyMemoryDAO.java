package com.noah.object.reservation.procedural.reservation.persistence.memory;

import com.noah.object.reservation.procedural.reservation.domain.DiscountPolicy;
import com.noah.object.reservation.procedural.reservation.persistence.DiscountPolicyDAO;

public class DiscountPolicyMemoryDAO extends InMemoryDAO<DiscountPolicy> implements DiscountPolicyDAO {
    @Override
    public DiscountPolicy selectDiscountPolicy(Long movieId) {
        return findOne(policy -> policy.getMovieId().equals(movieId));
    }
}
