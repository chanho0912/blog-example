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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPolicyId() {
        return policyId;
    }

    public void setPolicyId(Long policyId) {
        this.policyId = policyId;
    }

    public boolean isPeriodCondition() {
        return ConditionType.PERIOD_CONDITION.equals(conditionType);
    }

    public TimeInterval getInterval() {
        return interval;
    }

    public void setInterval(TimeInterval interval) {
        this.interval = interval;
    }

    public boolean isSequenceCondition() {
        return ConditionType.SEQUENCE_CONDITION.equals(conditionType);
    }

    // 요구사항 추가
    public boolean isCombinedCondition() {
        return ConditionType.COMBINED_CONDITION.equals(conditionType);
    }

    public ConditionType getConditionType() {
        return conditionType;
    }

    public void setConditionType(ConditionType conditionType) {
        this.conditionType = conditionType;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }
}
