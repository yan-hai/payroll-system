package com.nobodyhub.payroll.core.item.calendar;

import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

/**
 * @author yan_h
 * @since 2018/6/1
 */
public class CalendarSumItemTest extends CalendarItemTest {
    @Test
    @Override
    public void testDefaultValue() {
        CalendarSumItem item = new CalendarSumItem("item");
        assertEquals(BigDecimal.ZERO, item.defaultValue());
        assertEquals("item", item.build().getId());
    }

    @Test
    public void testGetValue() throws PayrollCoreException {
        CalendarSumItem item = new CalendarSumItem("item");
        addValues(item);
        assertEquals(new BigDecimal("35.0"),
                item.getValue(LocalDate.of(2018, 1, 1)));
    }
}