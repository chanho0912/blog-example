package com.noah.object.reservation.procedural.reservation.domain;

import com.noah.object.reservation.procedural.generic.TimeInterval;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Screening {
    private Long id;
    private Long movieId;
    private Integer sequence;
    private LocalDateTime screeningTime;

    public Screening() {
    }

    public Screening(Long movieId, Integer sequence, LocalDateTime screeningTime) {
        this(null, movieId, sequence, screeningTime);
    }

    public Screening(Long id, Long movieId, Integer sequence, LocalDateTime screeningTime) {
        this.id = id;
        this.movieId = movieId;
        this.sequence = sequence;
        this.screeningTime = screeningTime;
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

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public LocalDateTime getScreeningTime() {
        return screeningTime;
    }

    public void setScreeningTime(LocalDateTime screeningTime) {
        this.screeningTime = screeningTime;
    }

    public boolean isPlayedIn(DayOfWeek dayOfWeek, TimeInterval interval) {
        return this.screeningTime.getDayOfWeek().equals(dayOfWeek) &&
                interval.isIncluded(this.screeningTime.toLocalTime());
    }
}
