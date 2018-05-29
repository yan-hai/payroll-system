package com.nobodyhub.payroll.core.util;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.Assert.assertEquals;

/**
 * @author yan_h
 * @since 29/05/2018
 */
public class DateFormatUtilsTest {
    @Test
    public void testParseDate() {
        assertEquals(LocalDate.of(2018, 4, 1),
                DateFormatUtils.parseDate("20180401"));
    }

    @Test
    public void testParseTime() {
        assertEquals(LocalTime.of(16, 11, 12),
                DateFormatUtils.parseTime("161112"));
        assertEquals(LocalTime.of(00, 11, 12),
                DateFormatUtils.parseTime("001112"));
    }

    @Test
    public void testParseDateTime() {
        assertEquals(LocalDateTime.of(2018, 4, 1, 0, 1, 2),
                DateFormatUtils.parseDateTime("20180401000102"));
    }

    @Test
    public void testConvertDate() {
        assertEquals("20180401",
                DateFormatUtils.convertDate(LocalDate.of(2018, 4, 1)));
    }

    @Test
    public void testConvertTime() {
        assertEquals("161112",
                DateFormatUtils.convertTime(LocalTime.of(16, 11, 12)));
    }

    @Test
    public void testConvertDateTime() {
        assertEquals("20180401000102",
                DateFormatUtils.convertDateTime(LocalDateTime.of(2018, 4, 1, 0, 1, 2)));
    }
}