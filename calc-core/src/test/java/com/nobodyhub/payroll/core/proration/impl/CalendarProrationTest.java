package com.nobodyhub.payroll.core.proration.impl;

import com.google.common.collect.Maps;
import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.proration.abstr.ProrationTest;
import org.junit.Before;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

/**
 * @author Ryan
 */
public class CalendarProrationTest extends ProrationTest<CalendarProration> {


    private Map<LocalDate, BigDecimal> beforeValues = Maps.newHashMap();

    @Before
    public void setup() throws PayrollCoreException {
        super.setup();
        this.proration = new CalendarProration("id", "calendarId");

        beforeValues.put(LocalDate.of(2018, 5, 1), new BigDecimal("3000"));
        beforeValues.put(LocalDate.of(2018, 5, 16), new BigDecimal("6000"));

    }
}