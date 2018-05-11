package com.nobodyhub.payroll.core.proration.impl;

import com.google.common.collect.Maps;
import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.item.calendar.CalendarItem;
import com.nobodyhub.payroll.core.item.calendar.Period;
import com.nobodyhub.payroll.core.proration.abstr.Proration;
import com.nobodyhub.payroll.core.task.execution.ExecutionContext;
import com.nobodyhub.payroll.core.util.PayrollCoreConst;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.SortedMap;

/**
 * @author yan_h
 * @since 2018-05-10.
 */
public class CalendarProration extends Proration {

    public CalendarProration(String id, String calendarItemId) {
        super(id, calendarItemId);
    }

    @Override
    public SortedMap<LocalDate, BigDecimal> prorate(ExecutionContext context, SortedMap<LocalDate, BigDecimal> beforeValues) throws PayrollCoreException {
        SortedMap<Period, BigDecimal> periodValues = convertToPeriod(beforeValues);
        CalendarItem item = context.get(calendarItemId, CalendarItem.class);
        return prorate(item, periodValues);
    }

    protected SortedMap<LocalDate, BigDecimal> prorate(CalendarItem item, SortedMap<Period, BigDecimal> data) throws PayrollCoreException {
        SortedMap<LocalDate, BigDecimal> calendar = item.getValues();
        BigDecimal totalVal = calendar.values().stream().reduce(BigDecimal.ZERO, (a, b) -> (a.add(b)));
        SortedMap<LocalDate, BigDecimal> resultMap = Maps.newTreeMap();
        for (Period period : data.keySet()) {
            BigDecimal periodVal = BigDecimal.ZERO;
            for (Map.Entry<LocalDate, BigDecimal> entry : calendar.entrySet()) {
                LocalDate sDate = entry.getKey();
                if (period.isAfter(sDate)) {
                    // sdate is before period
                    continue;
                }
                if (period.contains(sDate)) {
                    periodVal = periodVal.add(entry.getValue());
                } else {
                    // sdate is after period
                    break;
                }
            }
            resultMap.put(period.getStart(),
                    data.get(period).multiply(periodVal).divide(totalVal, PayrollCoreConst.MATH_CONTEXT));
        }
        return resultMap;
    }
}
