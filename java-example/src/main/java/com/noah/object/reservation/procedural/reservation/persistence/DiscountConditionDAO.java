package com.noah.object.reservation.procedural.reservation.persistence;

import com.noah.object.reservation.procedural.reservation.domain.DiscountCondition;

import java.util.List;

public interface DiscountConditionDAO {
    List<DiscountCondition> selectDiscountConditions(Long policyId);

    void insert(DiscountCondition discountCondition);
}
