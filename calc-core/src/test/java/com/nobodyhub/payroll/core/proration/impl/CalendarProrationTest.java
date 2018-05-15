package com.nobodyhub.payroll.core.proration.impl;

import com.google.common.collect.Maps;
import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.item.calendar.Period;
import com.nobodyhub.payroll.core.proration.abstr.ProrationTest;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.SortedMap;

import static org.junit.Assert.assertEquals;

/**
 * @author Ryan
 */
public class CalendarProrationTest extends ProrationTest<CalendarProration> {

    @Before
    public void setup() throws PayrollCoreException {
        super.setup();
        this.proration = new CalendarProration("id", "calendarId");
    }

    @Test
    public void testProrate() throws PayrollCoreException {
        SortedMap<LocalDate, BigDecimal> result = proration.prorate(executionContext, beforeValues);
        assertEquals(3, result.size());
        assertEquals(new BigDecimal("1500"), result.get(LocalDate.of(2018, 5, 2)));
        assertEquals(new BigDecimal("1250"), result.get(LocalDate.of(2018, 5, 10)));
        assertEquals(new BigDecimal("625"), result.get(LocalDate.of(2018, 5, 20)));

    }

    @Test
    public void testProratePeriod() throws PayrollCoreException {
        SortedMap<Period, BigDecimal> data = Maps.newTreeMap();
        data.put(Period.of("20180502", "20180509"), new BigDecimal("3000"));
        data.put(Period.of("20180510", "20180519"), new BigDecimal("4000"));
        data.put(Period.of("20180520", "20180531"), new BigDecimal("5000"));
        SortedMap<LocalDate, BigDecimal> result = proration.proratePeriod(calendarItem,
                data,
                period);
        assertEquals(3, result.size());
        assertEquals(new BigDecimal("1500"), result.get(LocalDate.of(2018, 5, 2)));
        assertEquals(new BigDecimal("1250"), result.get(LocalDate.of(2018, 5, 10)));
        assertEquals(new BigDecimal("625"), result.get(LocalDate.of(2018, 5, 20)));
    }

    @Test
    public void testUnzip() throws PayrollCoreException {
        SortedMap<LocalDate, BigDecimal> data = Maps.newTreeMap();
        data.put(LocalDate.of(2018, 4, 20), BigDecimal.ONE);
        data.put(LocalDate.of(2018, 4, 30), BigDecimal.ZERO);
        data.put(LocalDate.of(2018, 5, 2), BigDecimal.ONE);
        data.put(LocalDate.of(2018, 5, 10), BigDecimal.ZERO);
        data.put(LocalDate.of(2018, 5, 20), BigDecimal.ONE);
        SortedMap<LocalDate, BigDecimal> result = proration.unzip(data,
                Period.of("20180501", "20180531"));
        assertEquals(31, result.size());
        assertEquals(BigDecimal.ZERO, result.get(LocalDate.of(2018, 5, 1)));
        assertEquals(BigDecimal.ONE, result.get(LocalDate.of(2018, 5, 2)));
        assertEquals(BigDecimal.ONE, result.get(LocalDate.of(2018, 5, 9)));
        assertEquals(BigDecimal.ZERO, result.get(LocalDate.of(2018, 5, 10)));
        assertEquals(BigDecimal.ZERO, result.get(LocalDate.of(2018, 5, 19)));
        assertEquals(BigDecimal.ONE, result.get(LocalDate.of(2018, 5, 20)));
        assertEquals(BigDecimal.ONE, result.get(LocalDate.of(2018, 5, 30)));
    }
}