package com.nobodyhub.payroll.core.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Ryan
 */
public final class DateFormatUtils {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HHmmss");
    private static final DateTimeFormatter DATETIME_FORMAT = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    public static LocalDate parseDate(String date) {
        return LocalDate.parse(date, DATE_FORMAT);
    }

    public static LocalTime parseTime(String date) {
        return LocalTime.parse(date, TIME_FORMAT);
    }

    public static LocalDateTime parseDateTime(String date) {
        return LocalDateTime.parse(date, DATETIME_FORMAT);
    }

    public static String convertDate(LocalDate date) {
        return date.format(DATE_FORMAT);
    }

    public static String convertDate(LocalTime date) {
        return date.format(TIME_FORMAT);
    }

    public static String convertDate(LocalDateTime date) {
        return date.format(DATETIME_FORMAT);
    }
}
