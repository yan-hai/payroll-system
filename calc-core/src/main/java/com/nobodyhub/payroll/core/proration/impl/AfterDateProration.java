package com.nobodyhub.payroll.core.proration.impl;

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
public class AfterDateProration extends CalendarProration {
    protected final LocalDate effectiveDate;

    public AfterDateProration(String prorationId, String calendarItemId, LocalDate effectiveDate) {
        super(prorationId, calendarItemId);
        this.effectiveDate = effectiveDate;
    }

    @Override
    public SortedMap<LocalDate, BigDecimal> prorate(ExecutionContext context, SortedMap<LocalDate, BigDecimal> beforeValues) throws PayrollCoreException {
        SortedMap<Period, BigDecimal> periodValues = convertValueToPeriod(super.prorate(context, beforeValues), context.getPeriod());
        SortedMap<LocalDate, BigDecimal> result = Maps.newTreeMap();
        for (SortedMap.Entry<Period, BigDecimal> entry : periodValues.entrySet()) {
            Period period = entry.getKey();
            if (period.isBefore(effectiveDate)) {
                result.put(period.getStart(), BigDecimal.ZERO);
            } else if (period.contains(effectiveDate)) {
                result.put(effectiveDate, entry.getValue());
            } else {
                //period is after the effective
                result.put(period.getStart(), entry.getValue());
            }
        }
        return result;
    }
}
