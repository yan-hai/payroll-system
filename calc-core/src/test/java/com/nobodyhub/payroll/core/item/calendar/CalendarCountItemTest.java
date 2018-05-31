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
public class CalendarCountItemTest extends CalendarItemTest {
    @Test
    @Override
    public void testDefaultValue() {
        CalendarCountItem item = new CalendarCountItem("item");
        assertEquals(BigDecimal.ZERO, item.defaultValue());
        assertEquals("item", item.build().getId());
    }

    @Test
    public void testGetValue() throws PayrollCoreException {
        CalendarCountItem item = new CalendarCountItem("item");
        addValues(item);
        assertEquals(new BigDecimal("21"),
                item.getValue(LocalDate.of(2018, 1, 1)));
    }
}