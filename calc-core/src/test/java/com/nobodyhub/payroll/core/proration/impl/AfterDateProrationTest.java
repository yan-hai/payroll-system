package com.nobodyhub.payroll.core.proration.impl;

import com.nobodyhub.payroll.core.exception.PayrollCoreException;
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
public class AfterDateProrationTest extends ProrationTest<AfterDateProration> {
    @Before
    public void setup() throws PayrollCoreException {
        super.setup();
        proration = new AfterDateProration("id",
                "calendarId",
                "hrDateItemId");
    }

    @Test
    public void testProrate() throws PayrollCoreException {
        SortedMap<LocalDate, BigDecimal> result = proration.prorate(executionContext, beforeValues);
        assertEquals(3, result.size());
        assertEquals(new BigDecimal("0"), result.get(LocalDate.of(2018, 5, 2)));
        assertEquals(new BigDecimal("250"), result.get(LocalDate.of(2018, 5, 10)));
        assertEquals(new BigDecimal("625"), result.get(LocalDate.of(2018, 5, 20)));
        assertEquals(new BigDecimal("875"), this.proration.getFinalValue(executionContext, beforeValues));
    }
}