package com.nobodyhub.payroll.core.proration.abstr;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.item.calendar.CalendarItem;
import com.nobodyhub.payroll.core.item.calendar.Period;
import com.nobodyhub.payroll.core.task.execution.ExecutionContext;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

import static org.junit.Assert.assertEquals;

/**
 * @author Ryan
 */
public abstract class ProrationTest<T extends Proration> {
    protected T proration;

    @Mock
    private ExecutionContext executionContext;

    private CalendarItem calendarItem = new CalendarItem("calendarId");

    @Before
    public void setup() throws PayrollCoreException {
        calendarItem.add(LocalDate.of(2018, 5, 1), BigDecimal.ONE);
        calendarItem.add(LocalDate.of(2018, 5, 10), BigDecimal.ZERO);
        calendarItem.add(LocalDate.of(2018, 5, 20), BigDecimal.ONE);

        MockitoAnnotations.initMocks(this);
        Mockito.when(executionContext.get("calendarId")).thenReturn(calendarItem);
    }

    @Test
    public void testConvertValueToPeriod() throws PayrollCoreException {
        Map<LocalDate, BigDecimal> beforeValues = Maps.newHashMap();
        beforeValues.put(LocalDate.of(2018, 5, 2), BigDecimal.ONE);
        beforeValues.put(LocalDate.of(2018, 5, 10), BigDecimal.ZERO);
        beforeValues.put(LocalDate.of(2018, 5, 20), BigDecimal.ONE);

        SortedMap<Period, BigDecimal> result = proration.convertValueToPeriod(beforeValues, Period.of("20180501", "20180531"));
        assertEquals(3, result.size());
        assertEquals(BigDecimal.ONE, result.get(Period.of("20180502", "20180509")));
        assertEquals(BigDecimal.ZERO, result.get(Period.of("20180510", "20180519")));
        assertEquals(BigDecimal.ONE, result.get(Period.of("20180520", "20180531")));
    }


    @Test
    public void testConvertDateToPeriod() throws PayrollCoreException {
        Set<LocalDate> dates = Sets.newHashSet();
        dates.add(LocalDate.of(2018, 4, 25));
        dates.add(LocalDate.of(2018, 5, 2));
        dates.add(LocalDate.of(2018, 5, 10));
        dates.add(LocalDate.of(2018, 5, 20));

        List<Period> result = proration.convertDateToPeriod(dates, Period.of("20180501", "20180531"));
        assertEquals(4, result.size());
        assertEquals(Period.of("20180501", "20180501"), result.get(0));
        assertEquals(Period.of("20180502", "20180509"), result.get(1));
        assertEquals(Period.of("20180510", "20180519"), result.get(2));
        assertEquals(Period.of("20180520", "20180531"), result.get(3));

        result = proration.convertDateToPeriod(dates, Period.of("20180501", "20180515"));
        assertEquals(3, result.size());
        assertEquals(Period.of("20180501", "20180501"), result.get(0));
        assertEquals(Period.of("20180502", "20180509"), result.get(1));
        assertEquals(Period.of("20180510", "20180515"), result.get(2));

        result = proration.convertDateToPeriod(dates, Period.of("20180420", "20180515"));
        assertEquals(3, result.size());
        assertEquals(Period.of("20180425", "20180501"), result.get(0));
        assertEquals(Period.of("20180502", "20180509"), result.get(1));
        assertEquals(Period.of("20180510", "20180515"), result.get(2));

        result = proration.convertDateToPeriod(dates, Period.of("20180420", "20180531"));
        assertEquals(4, result.size());
        assertEquals(Period.of("20180425", "20180501"), result.get(0));
        assertEquals(Period.of("20180502", "20180509"), result.get(1));
        assertEquals(Period.of("20180510", "20180519"), result.get(2));
        assertEquals(Period.of("20180520", "20180531"), result.get(3));
    }
}