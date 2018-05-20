package com.nobodyhub.payroll.core.proration.impl;

import com.google.common.collect.Maps;
import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.item.calendar.CalendarItem;
import com.nobodyhub.payroll.core.item.calendar.Period;
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
public class BeforeDateProration extends CalendarProration {
    /**
     * end date of effective period, inclusive
     */
    protected final LocalDate effectiveDate;

    public BeforeDateProration(String prorationId, String calendarItemId, LocalDate effectiveDate) {
        super(prorationId, calendarItemId);
        this.effectiveDate = effectiveDate;
    }

    /**
     * Prorate the data within given period using calender item
     * Only the values equal or before {@link this#effectiveDate} will be considerred
     *
     * @param item
     * @param data
     * @param period
     * @return
     * @throws PayrollCoreException
     */
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
