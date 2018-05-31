package com.nobodyhub.payroll.core.item.calendar;

import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

/**
 * @author yan_h
 * @since 2018/5/31
 */
public class CalendarItemTest {
    @Test
    public void testDefaultValue() {
        CalendarItem item = new CalendarItem("item");
        assertEquals(BigDecimal.ZERO, item.defaultValue());
        assertEquals("item", item.build().getId());
    }

    protected void addValues(CalendarItem item) throws PayrollCoreException {
        LocalDate date = LocalDate.of(2018, 5, 1);
        for (int idx = 1; idx <= 10; idx++) {
            item.add(date, BigDecimal.ZERO);
            date = date.plusDays(1);
        }
        for (int idx = 11; idx <= 15; idx++) {
            item.add(date, BigDecimal.ONE);
            date = date.plusDays(1);
        }
        for (int idx = 16; idx <= 19; idx++) {
            item.add(date, new BigDecimal("1.5"));
            date = date.plusDays(1);
        }
        for (int idx = 20; idx <= 31; idx++) {
            item.add(date, new BigDecimal("2"));
            date = date.plusDays(1);
        }
    }
}