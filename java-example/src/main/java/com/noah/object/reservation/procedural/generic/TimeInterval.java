package com.noah.object.reservation.procedural.generic;

import java.time.LocalTime;

public class TimeInterval {
    private final LocalTime startTime;
    private final LocalTime endTime;

    public TimeInterval(LocalTime startTime, LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public boolean isOverlapped(TimeInterval target) {
        return this.endTime.compareTo(target.startTime) > 0 && this.startTime.compareTo(target.endTime) < 0;
    }

    public boolean isIncluded(LocalTime localTime) {
        return !localTime.isBefore(startTime) && !localTime.isAfter(endTime);
    }
}
