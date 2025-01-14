package com.noah.object.reservation.procedural.reservation.persistence.memory;

import com.noah.object.reservation.procedural.reservation.domain.DiscountCondition;
import com.noah.object.reservation.procedural.reservation.persistence.DiscountConditionDAO;

import java.util.List;

public class DiscountConditionMemoryDAO extends InMemoryDAO<DiscountCondition> implements DiscountConditionDAO {
    @Override
    public List<DiscountCondition> selectDiscountConditions(Long policyId) {
        return findMany(condition -> condition.getPolicyId().equals(policyId));
    }
}
