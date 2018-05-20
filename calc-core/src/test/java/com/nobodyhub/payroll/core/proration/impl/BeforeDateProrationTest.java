package com.nobodyhub.payroll.core.proration.impl;

import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.proration.abstr.ProrationTest;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.SortedMap;

import static org.junit.Assert.*;

/**
 * @author Ryan
 */
public class BeforeDateProrationTest extends ProrationTest<BeforeDateProration> {
    @Before
    public void setup() throws PayrollCoreException {
        super.setup();
        proration = new BeforeDateProration("id",
                "calendarId",
                LocalDate.of(2018, 5, 14));
    }

    @Test
    public void testProrate() throws PayrollCoreException {
        SortedMap<LocalDate, BigDecimal> result = proration.prorate(executionContext, beforeValues);
        assertEquals(3, result.size());
        assertEquals(new BigDecimal("1500"), result.get(LocalDate.of(2018, 5, 2)));
        assertEquals(new BigDecimal("1250"), result.get(LocalDate.of(2018, 5, 10)));
        assertEquals(new BigDecimal("0"), result.get(LocalDate.of(2018, 5, 20)));
        assertEquals(new BigDecimal("2750"), this.proration.getFinalValue(executionContext, beforeValues));
    }
}