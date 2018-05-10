package com.nobodyhub.payroll.core.proration;

import com.google.common.collect.Maps;
import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.item.calendar.Period;
import com.nobodyhub.payroll.core.task.execution.ExecutionContext;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.SortedMap;

/**
 * @author yan_h
 * @since 2018-05-10.
 */
public class BeforeDateProration extends CalendarProration {
    protected final LocalDate effectiveDate;

    public BeforeDateProration(String prorationId, String calendarItemId, LocalDate effectiveDate) {
        super(prorationId, calendarItemId);
        this.effectiveDate = effectiveDate;
    }

    @Override
    public SortedMap<LocalDate, BigDecimal> prorate(ExecutionContext context, SortedMap<LocalDate, BigDecimal> beforeValues) throws PayrollCoreException {
        SortedMap<Period, BigDecimal> periodValues = convertToPeriod(beforeValues);
        SortedMap<LocalDate, BigDecimal> result = Maps.newTreeMap();
        for (SortedMap.Entry<Period, BigDecimal> entry : periodValues.entrySet()) {
            Period period = entry.getKey();
            if (period.isBefore(effectiveDate) || period.contains(effectiveDate)) {
                result.put(period.getStart(), entry.getValue());
            } else {
                //period is after the effective
                result.put(effectiveDate, BigDecimal.ZERO);
            }
        }
        return result;
    }
}
