package com.noah.javaexample;

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
import com.noah.object.reservation.procedural.reservation.persistence.memory.DiscountConditionMemoryDAO;
import com.noah.object.reservation.procedural.reservation.persistence.memory.DiscountPolicyMemoryDAO;
import com.noah.object.reservation.procedural.reservation.persistence.memory.MovieMemoryDAO;
import com.noah.object.reservation.procedural.reservation.persistence.memory.ReservationMemoryDAO;
import com.noah.object.reservation.procedural.reservation.persistence.memory.ScreeningMemoryDAO;
import com.noah.object.reservation.procedural.reservation.service.ReservationService;

import java.time.LocalDateTime;

import static com.noah.object.reservation.procedural.reservation.domain.DiscountCondition.ConditionType.PERIOD_CONDITION;
import static com.noah.object.reservation.procedural.reservation.domain.DiscountCondition.ConditionType.SEQUENCE_CONDITION;
import static com.noah.object.reservation.procedural.reservation.domain.DiscountPolicy.PolicyType.AMOUNT_POLICY;
import static java.time.DayOfWeek.MONDAY;
import static java.time.DayOfWeek.WEDNESDAY;
import static java.time.LocalTime.of;

/**
 * 기본적으로 언체크(런타임 예외)를 사용하자.
 * 체크 예외는 비즈니스 로직 상 의도적으로 던지는 예외에만 사용.
 * 이 경우 해당 예외를 잡아서 반드시 처리해야 하는 문제일 때만 체크 예외를 사용한다.
 * ex) 계좌 이체 실패 예외, 결제 시 포인트 부족 예외, 로그인 실패
 * 물론 이 경우에도 100% 체크 예외로 만들어야 하는 것은 아니다. 다만 계좌 이체 실패처럼 매우 심각한 문제는 개발자가 실수로 예외를 놓치면 안된다고 판단할 수 있다.
 * 이 경우 체크 예외로 만들어 두면 컴파일러를 통해 놓친 예외를 인지할 수 있다.
 */
public class JavaExampleApplication {
    private ScreeningDAO screeningDAO = new ScreeningMemoryDAO();
    private MovieDAO movieDAO = new MovieMemoryDAO();
    private DiscountPolicyDAO discountPolicyDAO = new DiscountPolicyMemoryDAO();
    private DiscountConditionDAO discountConditionDAO = new DiscountConditionMemoryDAO();
    private ReservationDAO reservationDAO = new ReservationMemoryDAO();

    ReservationService reservationService = new ReservationService(
            screeningDAO,
            movieDAO,
            discountPolicyDAO,
            discountConditionDAO,
            reservationDAO);


    private Screening initializeData() {
        Movie movie = new Movie("한산", 150, Money.wons(10000));
        movieDAO.insert(movie);

        DiscountPolicy discountPolicy = new DiscountPolicy(movie.getId(), AMOUNT_POLICY, Money.wons(1000), null);
        discountPolicyDAO.insert(discountPolicy);

        discountConditionDAO.insert(new DiscountCondition(discountPolicy.getId(), SEQUENCE_CONDITION, null, null, null, 1));
        discountConditionDAO.insert(new DiscountCondition(discountPolicy.getId(), SEQUENCE_CONDITION, null, null, null, 10));
        discountConditionDAO.insert(new DiscountCondition(discountPolicy.getId(), PERIOD_CONDITION, MONDAY, of(10, 0), of(12, 0), null));
        discountConditionDAO.insert(new DiscountCondition(discountPolicy.getId(), PERIOD_CONDITION, WEDNESDAY, of(18, 0), of(21, 0), null));

        Screening screening = new Screening(movie.getId(), 7, LocalDateTime.of(2024, 12, 11, 18, 0));
        screeningDAO.insert(screening);

        return screening;
    }

    public void run() {
        Screening screening = initializeData();

        Reservation reservation = reservationService.reserveScreening(1L, screening.getId(), 2);

        System.out.printf("관객수 : %d, 요금: %s%n", reservation.getAudienceCount(), reservation.getFee());
    }

    public static void main(String[] args) {
        new JavaExampleApplication().run();
    }
}
