package com.nobodyhub.payroll.core.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Date format utilities
 * @author Ryan
 */
public final class DateFormatUtils {
    /**
     * Date Format
     */
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd");
    /**
     * Time Format
     */
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HHmmss");
    /**
     * DateTime Format
     */
    private static final DateTimeFormatter DATETIME_FORMAT = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    /**
     * Parse date from String
     * @param date
     * @return
     */
    public static LocalDate parseDate(String date) {
        return LocalDate.parse(date, DATE_FORMAT);
    }

    /**
     * Parse time from String
     * @param date
     * @return
     */
    public static LocalTime parseTime(String date) {
        return LocalTime.parse(date, TIME_FORMAT);
    }

    /**
     * Parse datetime from String
     * @param date
     * @return
     */
    public static LocalDateTime parseDateTime(String date) {
        return LocalDateTime.parse(date, DATETIME_FORMAT);
    }

    /**
     * convert date to String
     * @param date
     * @return
     */
    public static String convertDate(LocalDate date) {
        return date.format(DATE_FORMAT);
    }

    /**
     * convert time to String
     * @param date
     * @return
     */
    public static String convertDate(LocalTime date) {
        return date.format(TIME_FORMAT);
    }

    /**
     * convert datetime to String
     * @param date
     * @return
     */
    public static String convertDate(LocalDateTime date) {
        return date.format(DATETIME_FORMAT);
    }
}
