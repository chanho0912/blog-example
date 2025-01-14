package com.noah.object.reservation.procedural.reservation.domain;

import com.noah.object.reservation.procedural.generic.TimeInterval;

import java.time.DayOfWeek;

public class DiscountCondition {
    public enum ConditionType {
        PERIOD_CONDITION, SEQUENCE_CONDITION, COMBINED_CONDITION // 요구사항 추가
    }
    ;

    private Long id;
    private Long policyId;
    private ConditionType conditionType;
    private DayOfWeek dayOfWeek;
    private TimeInterval interval;
    private Integer sequence;

    public DiscountCondition() {
    }

    public DiscountCondition(Long policyId, ConditionType conditionType, DayOfWeek dayOfWeek, TimeInterval interval, Integer sequence) {
        this(null, policyId, conditionType, dayOfWeek, interval, sequence);
    }

    public DiscountCondition(Long id, Long policyId, ConditionType conditionType, DayOfWeek dayOfWeek, TimeInterval interval, Integer sequence) {
        this.id = id;
        this.policyId = policyId;
        this.conditionType = conditionType;
        this.dayOfWeek = dayOfWeek;
        this.interval = interval;
        this.sequence = sequence;
    }

    // Discount Condition으로 책임 이동.
    public boolean isSatisfiedBy(Screening screening) {
        if (isPeriodCondition()) {
            return screening.isPlayedIn(dayOfWeek, interval);
        } else if (isSequenceCondition()) {
            return sequence.equals(screening.getSequence());
        } else if (isCombinedCondition()) {
            return sequence.equals(screening.getSequence()) &&
                    screening.isPlayedIn(dayOfWeek, interval);
        }

        return false;
    }

    public Long getPolicyId() {
        return policyId;
    }

    private boolean isPeriodCondition() {
        return ConditionType.PERIOD_CONDITION.equals(conditionType);
    }

    private boolean isSequenceCondition() {
        return ConditionType.SEQUENCE_CONDITION.equals(conditionType);
    }

    // 요구사항 추가
    private boolean isCombinedCondition() {
        return ConditionType.COMBINED_CONDITION.equals(conditionType);
    }

}
