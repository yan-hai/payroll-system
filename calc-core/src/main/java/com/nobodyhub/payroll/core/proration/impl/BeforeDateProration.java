package com.nobodyhub.payroll.core.proration.impl;

import com.google.common.collect.Maps;
import com.nobodyhub.payroll.core.common.Period;
import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.item.calendar.CalendarItem;
import com.nobodyhub.payroll.core.item.hr.HrDateItem;
import com.nobodyhub.payroll.core.task.execution.ExecutionContext;
import com.nobodyhub.payroll.core.util.PayrollCoreConst;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.SortedMap;

/**
 * Prorate based on calendar information equal/before a given date
 *
 * @author yan_h
 * @since 2018-05-10.
 */
public class BeforeDateProration extends AfterDateProration {

    public BeforeDateProration(String prorationId, String calendarItemId, String hrDateItemId) {
        super(prorationId, calendarItemId, hrDateItemId);
    }

    @Override
    public SortedMap<LocalDate, BigDecimal> prorate(ExecutionContext context,
                                                    SortedMap<LocalDate, BigDecimal> beforeValues)
            throws PayrollCoreException {
        SortedMap<Period, BigDecimal> periodValues = convertValueToPeriod(beforeValues, context.getPeriod());
        CalendarItem calendarItem = context.get(calendarItemId, CalendarItem.class);
        HrDateItem hrDateItem = context.get(hrDateItemId, HrDateItem.class);
        return proratePeriod(calendarItem, hrDateItem, periodValues, context.getPeriod());
    }

    /**
     * Prorate the data within given period using calender item
     * Only the values equal or before {@link this#hrDateItemId} will be considerred
     *
     * @param calendarItem
     * @param hrDateItem
     * @param data
     * @param period
     * @return
     * @throws PayrollCoreException
     */
    @Override
    protected SortedMap<LocalDate, BigDecimal> proratePeriod(CalendarItem calendarItem,
                                                             HrDateItem hrDateItem,
                                                             SortedMap<Period, BigDecimal> data,
                                                             Period period) throws PayrollCoreException {
        SortedMap<LocalDate, BigDecimal> calendar = unzip(calendarItem.getValues(), period);
        LocalDate effectiveDate = hrDateItem.getValue(period.getBaseDate());
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
                    //within the period and date equals or before effective date
                    if (!sDate.isAfter(effectiveDate)) {
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
