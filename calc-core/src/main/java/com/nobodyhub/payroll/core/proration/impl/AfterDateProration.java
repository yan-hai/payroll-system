package com.nobodyhub.payroll.core.proration.impl;

import com.google.common.collect.Maps;
import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.item.calendar.CalendarItem;
import com.nobodyhub.payroll.core.item.calendar.Period;
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
public class AfterDateProration extends CalendarProration {
    protected final LocalDate effectiveDate;

    public AfterDateProration(String prorationId, String calendarItemId, LocalDate effectiveDate) {
        super(prorationId, calendarItemId);
        this.effectiveDate = effectiveDate;
    }

    @Override
    protected SortedMap<LocalDate, BigDecimal> proratePeriod(CalendarItem item,
                                                             SortedMap<Period, BigDecimal> data,
                                                             Period period) throws PayrollCoreException {
        SortedMap<LocalDate, BigDecimal> calendar = unzip(item.getValues(), period);
        BigDecimal totalVal = calendar.values().stream().reduce(BigDecimal.ZERO, (a, b) -> (a.add(b)));

        SortedMap<LocalDate, BigDecimal> resultMap = Maps.newTreeMap();
        for (Period sub : data.keySet()) {
            BigDecimal periodVal = BigDecimal.ZERO;
            for (Map.Entry<LocalDate, BigDecimal> entry : calendar.entrySet()) {
                LocalDate sDate = entry.getKey();
                if (sub.isAfter(sDate)) {
                    // sdate is before period
                    continue;
                }
                if (sub.contains(sDate)) {
                    if (!sDate.isBefore(effectiveDate)) {
                        periodVal = periodVal.add(entry.getValue());
                    }
                } else {
                    // sdate is after period
                    break;
                }
            }
            resultMap.put(sub.getStart(),
                    data.get(sub)
                            .multiply(periodVal)
                            .divide(totalVal, PayrollCoreConst.MATH_CONTEXT));
        }
        return resultMap;
    }
}
