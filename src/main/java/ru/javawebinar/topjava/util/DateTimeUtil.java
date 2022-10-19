package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static boolean isBetweenHalfOpen(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) < 0;
    }

    public static LocalDate isStartDayNull(LocalDate localDate) {
        return localDate == null ? LocalDate.of(1,1,1) : localDate;
    }

    public static LocalDate isFinishDayNull(LocalDate localDate) {
        return localDate == null ? LocalDate.now() : localDate;
    }

    public static LocalTime isStartTimeNull(LocalTime localTime) {
        return localTime == null ? LocalTime.MIN : localTime;
    }
    public static LocalTime isFinishTimeNull(LocalTime localTime) {
        return localTime == null ? LocalTime.MAX : localTime;
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}

