package com.noah.object.reservation.procedural.reservation.persistence;

import com.noah.object.reservation.procedural.reservation.domain.DiscountPolicy;

public interface DiscountPolicyDAO {
    DiscountPolicy selectDiscountPolicy(Long movieId);

    void insert(DiscountPolicy discountPolicy);
}
