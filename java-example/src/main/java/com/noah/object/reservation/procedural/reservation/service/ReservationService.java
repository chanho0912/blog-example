package com.noah.object.reservation.procedural.reservation.service;

import com.noah.object.reservation.procedural.generic.Money;
import com.noah.object.reservation.procedural.reservation.domain.DiscountCondition;
import com.noah.object.reservation.procedural.reservation.domain.DiscountPolicy;
import com.noah.object.reservation.procedural.reservation.domain.Movie;
import com.noah.object.reservation.procedural.reservation.domain.Reservation;
import com.noah.object.reservation.procedural.reservation.domain.Screening;
import com.noah.object.reservation.procedural.reservation.persistence.DiscountConditionDAO;
import com.noah.object.reservation.procedural.reservation.persistence.DiscountPolicyDAO;
import com.noah.object.reservation.procedural.reservation.persistence.MovieDAO;
import com.noah.object.reservation.procedural.reservation.persistence.ReservationDAO;
import com.noah.object.reservation.procedural.reservation.persistence.ScreeningDAO;

import java.util.List;

/**
 * 절차적인 설계에 문제가 발생할 때
 * - 데이터를 구현한 코드가 변경될 때
 */
public class ReservationService {
    private ScreeningDAO screeningDAO;
    private MovieDAO movieDAO;
    private DiscountPolicyDAO discountPolicyDAO;
    private DiscountConditionDAO discountConditionDAO;
    private ReservationDAO reservationDAO;

    public ReservationService(ScreeningDAO screeningDAO,
                              MovieDAO movieDAO,
                              DiscountPolicyDAO discountPolicyDAO,
                              DiscountConditionDAO discountConditionDAO,
                              ReservationDAO reservationDAO) {
        this.screeningDAO = screeningDAO;
        this.movieDAO = movieDAO;
        this.discountConditionDAO = discountConditionDAO;
        this.discountPolicyDAO = discountPolicyDAO;
        this.reservationDAO = reservationDAO;
    }

    public Reservation reserveScreening(Long customerId, Long screeningId, Integer audienceCount) {
        Screening screening = screeningDAO.selectScreening(screeningId);
        Movie movie = movieDAO.selectMovie(screening.getMovieId());
        DiscountPolicy policy = discountPolicyDAO.selectDiscountPolicy(movie.getId());
        List<DiscountCondition> conditions = discountConditionDAO.selectDiscountConditions(policy.getId());

        DiscountCondition condition = findDiscountCondition(screening, conditions);

        Money fee;
        if (condition != null) {
            fee = movie.getFee().minus(calculateDiscount(policy, movie));
        } else {
            fee = movie.getFee();
        }

        Reservation reservation = makeReservation(customerId, screeningId, audienceCount, fee);
        reservationDAO.insert(reservation);

        return reservation;
    }

    private DiscountCondition findDiscountCondition(Screening screening, List<DiscountCondition> conditions) {
        for(DiscountCondition condition : conditions) {
            if (condition.isPeriodCondition()) {
                if (screening.isPlayedIn(condition.getDayOfWeek(),
                                         condition.getInterval())) {
                    return condition;
                }
            } else if (condition.isSequenceCondition()) {
                if (condition.getSequence().equals(screening.getSequence())) {
                    return condition;
                }
                // Discount Condition이 수정되었고, Process도 함께 수정되었음.
            } else if (condition.isCombinedCondition()) {
                if ((condition.getSequence().equals(screening.getSequence())) &&
                    (screening.isPlayedIn(condition.getDayOfWeek(),
                                          condition.getInterval()))) {
                    return condition;
                }
            }
        }

        return null;
    }

    private Money calculateDiscount(DiscountPolicy policy, Movie movie) {
        if (policy.isAmountPolicy()) {
            return policy.getAmount();
        } else if (policy.isPercentPolicy()) {
            return movie.getFee().times(policy.getPercent());
        }

        return Money.ZERO;
    }

    private Reservation makeReservation(Long customerId, Long screeningId, Integer audienceCount, Money fee) {
        return new Reservation(customerId, screeningId, audienceCount, fee.times(audienceCount));
    }
}
