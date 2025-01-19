package com.noah.object.reservation.procedural.reservation.domain;

import com.noah.object.reservation.procedural.generic.Money;

import java.util.ArrayList;
import java.util.List;

public class DiscountPolicy {
    public enum PolicyType { PERCENT_POLICY, AMOUNT_POLICY }

    private Long id;
    private Long movieId;
    private PolicyType policyType;
    private Money amount;
    private Double percent;
    private List<DiscountCondition> conditions = new ArrayList<>();

    public DiscountPolicy() {
    }

    public DiscountPolicy(Long movieId, PolicyType policyType, Money amount, Double percent, List<DiscountCondition> conditions) {
        this(null, movieId, policyType, amount, percent, conditions);
    }

    public DiscountPolicy(Long id, Long movieId, PolicyType policyType, Money amount, Double percent, List<DiscountCondition> conditions) {
        this.id = id;
        this.movieId = movieId;
        this.policyType = policyType;
        this.amount = amount;
        this.percent = percent;
        this.conditions = conditions;
    }

    public void addCondition(DiscountCondition discountCondition) {
        this.conditions.add(discountCondition);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public Money calculateDiscount(Movie movie, Screening screening) {
        DiscountCondition condition = findDiscountCondition(screening);

        if (condition == null) {
            return Money.ZERO;
        }

        if (isAmountPolicy()) {
            return amount;
        } else if (isPercentPolicy()) {
            return movie.getFee().times(percent);
        }

        return Money.ZERO;
    }

    public DiscountCondition findDiscountCondition(Screening screening) {
        for(DiscountCondition condition : conditions) {
            if (condition.isSatisfiedBy(screening)) {
                return condition;
            }
        }

        return null;
    }

    public boolean isAmountPolicy() {
        return PolicyType.AMOUNT_POLICY.equals(policyType);
    }

    public boolean isPercentPolicy() {
        return PolicyType.PERCENT_POLICY.equals(policyType);
    }

    public PolicyType getPolicyType() {
        return policyType;
    }

    public void setPolicyType(PolicyType policyType) {
        this.policyType = policyType;
    }

}
