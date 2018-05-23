package com.nobodyhub.payroll.core.util;

import com.google.common.collect.Sets;
import com.nobodyhub.payroll.core.common.Period;
import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * @author yan_h
 * @since 23/05/2018
 */
public class PeriodUtilsTest {
    @Test
    public void testConvertDateToPeriod() throws PayrollCoreException {
        Set<LocalDate> dates = Sets.newHashSet();
        dates.add(LocalDate.of(2018, 4, 25));
        dates.add(LocalDate.of(2018, 5, 2));
        dates.add(LocalDate.of(2018, 5, 10));
        dates.add(LocalDate.of(2018, 5, 20));

        List<Period> result = PeriodUtils.convertDateToPeriod(dates, Period.of("20180501", "20180531"));
        assertEquals(4, result.size());
        assertEquals(Period.of("20180501", "20180501"), result.get(0));
        assertEquals(Period.of("20180502", "20180509"), result.get(1));
        assertEquals(Period.of("20180510", "20180519"), result.get(2));
        assertEquals(Period.of("20180520", "20180531"), result.get(3));

        result = PeriodUtils.convertDateToPeriod(dates, Period.of("20180501", "20180515"));
        assertEquals(3, result.size());
        assertEquals(Period.of("20180501", "20180501"), result.get(0));
        assertEquals(Period.of("20180502", "20180509"), result.get(1));
        assertEquals(Period.of("20180510", "20180515"), result.get(2));

        result = PeriodUtils.convertDateToPeriod(dates, Period.of("20180420", "20180515"));
        assertEquals(3, result.size());
        assertEquals(Period.of("20180425", "20180501"), result.get(0));
        assertEquals(Period.of("20180502", "20180509"), result.get(1));
        assertEquals(Period.of("20180510", "20180515"), result.get(2));

        result = PeriodUtils.convertDateToPeriod(dates, Period.of("20180420", "20180531"));
        assertEquals(4, result.size());
        assertEquals(Period.of("20180425", "20180501"), result.get(0));
        assertEquals(Period.of("20180502", "20180509"), result.get(1));
        assertEquals(Period.of("20180510", "20180519"), result.get(2));
        assertEquals(Period.of("20180520", "20180531"), result.get(3));
    }
}