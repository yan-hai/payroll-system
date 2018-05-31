package com.nobodyhub.payroll.core.proration.abstr;

import com.google.common.collect.Maps;
import com.nobodyhub.payroll.core.common.Period;
import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.item.calendar.CalendarItem;
import com.nobodyhub.payroll.core.item.hr.HrDateItem;
import com.nobodyhub.payroll.core.task.execution.ExecutionContext;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.SortedMap;

import static org.junit.Assert.assertEquals;

/**
 * @author Ryan
 */
public abstract class ProrationTest<T extends Proration> {
    protected T proration;

    @Mock
    protected ExecutionContext executionContext;

    protected CalendarItem calendarItem = new CalendarItem("calendarId");

    protected HrDateItem hrDateItem = new HrDateItem("hrDateItemId");

    protected Period period;

    protected SortedMap<LocalDate, BigDecimal> beforeValues = Maps.newTreeMap();

    @Before
    public void setup() throws PayrollCoreException {
        initCalendarItem();

        hrDateItem.add(LocalDate.of(2018, 5, 10), LocalDate.of(2018, 5, 14));

        period = Period.of("20180501", "20180531", "20180525");

        beforeValues.put(LocalDate.of(2018, 5, 2), new BigDecimal("3000"));
        beforeValues.put(LocalDate.of(2018, 5, 10), new BigDecimal("4000"));
        beforeValues.put(LocalDate.of(2018, 5, 20), new BigDecimal("5000"));

        MockitoAnnotations.initMocks(this);
        Mockito.when(executionContext.get("calendarId", CalendarItem.class)).thenReturn(calendarItem);
        Mockito.when(executionContext.get("hrDateItemId", HrDateItem.class)).thenReturn(hrDateItem);
        Mockito.when(executionContext.getPeriod()).thenReturn(period);
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

    private void initCalendarItem() throws PayrollCoreException {
        LocalDate date = LocalDate.of(2018, 5, 1);
        //1~14
        for (int idx = 1; idx <= 14; idx++) {
            calendarItem.add(date, BigDecimal.ONE);
            date = date.plusDays(1);
        }
        //15~29
        for (int idx = 15; idx <= 29; idx++) {
            calendarItem.add(date, BigDecimal.ZERO);
            date = date.plusDays(1);
        }
        //30
        for (int idx = 30; idx <= 31; idx++) {
            calendarItem.add(date, BigDecimal.ONE);
            date = date.plusDays(1);
        }
    }
}