package com.nobodyhub.payroll.core.item.calendar;

import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.util.DateFormatUtils;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Ryan
 */
public class PeriodTest {
    private Period period = Period.of("20170101", "20180101");

    public PeriodTest() throws PayrollCoreException {
    }

    @Test(expected = PayrollCoreException.class)
    public void testException() throws PayrollCoreException {
        Period.of("20180514", "20180513");
    }

    @Test
    public void testOf() throws PayrollCoreException {
        Period.of("20180513", "20180513");
        Period.of("20180512", "20180513");
        Period.of("20100512", "20180513");
    }

    @Test
    public void testContains() {
        assertEquals(false, period.contains(DateFormatUtils.parseDate("20161231")));
        assertEquals(true, period.contains(DateFormatUtils.parseDate("20170101")));
        assertEquals(true, period.contains(DateFormatUtils.parseDate("20170601")));
        assertEquals(true, period.contains(DateFormatUtils.parseDate("20180101")));
        assertEquals(false, period.contains(DateFormatUtils.parseDate("20180102")));
    }

    @Test
    public void testOverlaps() throws PayrollCoreException {
        assertEquals(false, period.overlaps(Period.of("20180102", "20190101")));
        assertEquals(true, period.overlaps(Period.of("20180101", "20190101")));
        assertEquals(true, period.overlaps(Period.of("20160101", "20170101")));
        assertEquals(true, period.overlaps(Period.of("20160101", "20170601")));
        assertEquals(false, period.overlaps(Period.of("20160101", "20161231")));
    }

    @Test
    public void testIsAfter() {
        assertEquals(true, period.isAfter(DateFormatUtils.parseDate("20161231")));
        assertEquals(false, period.isAfter(DateFormatUtils.parseDate("20170101")));
        assertEquals(false, period.isAfter(DateFormatUtils.parseDate("20170601")));
        assertEquals(false, period.isAfter(DateFormatUtils.parseDate("20180101")));
        assertEquals(false, period.isAfter(DateFormatUtils.parseDate("20180102")));
    }

    @Test
    public void testIsBefore() {
        assertEquals(false, period.isBefore(DateFormatUtils.parseDate("20161231")));
        assertEquals(false, period.isBefore(DateFormatUtils.parseDate("20170101")));
        assertEquals(false, period.isBefore(DateFormatUtils.parseDate("20170601")));
        assertEquals(false, period.isBefore(DateFormatUtils.parseDate("20180101")));
        assertEquals(true, period.isBefore(DateFormatUtils.parseDate("20180102")));
    }

    @Test
    public void testCompareTo() throws PayrollCoreException {
        assertEquals(true, period.compareTo(Period.of("20161231", "20170101")) > 0);
        assertEquals(true, period.compareTo(Period.of("20170101", "20170101")) == 0);
        assertEquals(true, period.compareTo(Period.of("20170102", "20170103")) < 0);
    }
}