package com.nobodyhub.payroll.core.proration.impl;

import com.google.common.collect.Maps;
import com.nobodyhub.payroll.core.common.Period;
import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.item.calendar.CalendarItem;
import com.nobodyhub.payroll.core.proration.abstr.Proration;
import com.nobodyhub.payroll.core.task.execution.ExecutionContext;
import com.nobodyhub.payroll.core.util.PayrollCoreConst;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.SortedMap;

/**
 * Proration based on the values of {@link this#calendarItemId}
 *
 * @author yan_h
 * @since 2018-05-10.
 */
public class CalendarProration extends Proration {

    public CalendarProration(String id, String calendarItemId) {
        super(id, calendarItemId);
    }

    @Override
    public SortedMap<LocalDate, BigDecimal> prorate(ExecutionContext context,
                                                    Map<LocalDate, BigDecimal> beforeValues)
            throws PayrollCoreException {
        SortedMap<Period, BigDecimal> periodValues = convertValueToPeriod(beforeValues, context.getPeriod());
        CalendarItem item = context.get(calendarItemId, CalendarItem.class);
        return proratePeriod(item, periodValues, context.getPeriod());
    }

    /**
     * Prorate the data within given period using calender item
     *
     * @param item
     * @param data
     * @param period
     * @return
     * @throws PayrollCoreException
     */
    protected SortedMap<LocalDate, BigDecimal> proratePeriod(CalendarItem item,
                                                             SortedMap<Period, BigDecimal> data,
                                                             Period period)
            throws PayrollCoreException {
        //unzip the calender values
        SortedMap<LocalDate, BigDecimal> calendar = unzip(item.getValues(), period);
        //sumup the total value of calendar item
        BigDecimal totalVal = calendar.values().stream().reduce(BigDecimal.ZERO, (a, b) -> (a.add(b)));

        SortedMap<LocalDate, BigDecimal> resultMap = Maps.newTreeMap();
        for (Period sub : data.keySet()) {
            //calender value fall in the period
            BigDecimal periodVal = BigDecimal.ZERO;
            for (Map.Entry<LocalDate, BigDecimal> entry : calendar.entrySet()) {
                LocalDate sDate = entry.getKey();
                if (sub.isAfter(sDate)) {
                    // sdate is before period
                    continue;
                }
                if (sub.contains(sDate)) {
                    //sdate is within period
                    periodVal = periodVal.add(entry.getValue());
                } else {
                    // sdate is after period
                    break;
                }
            }
            // prorate the value in period by the calender value
            resultMap.put(sub.getStart(), data.get(sub)
                    .multiply(periodVal)
                    .divide(totalVal, PayrollCoreConst.MATH_CONTEXT));
        }
        return resultMap;
    }

    /**
     * unzip the compact values
     * e.g., for period 20180101~20180131
     * compact values:(size: 4)
     * 20180101: 1
     * 20180115: 0
     * 20180120: 1.5
     * 20180122: 1
     * unzipped values: (size:31)
     * 20180101~20180114: 1
     * 20180115~20180119: 0
     * 20180220~20180121: 1.5
     * 20180223~20180131: 1
     *
     * @param values values to be unzipped
     * @param period the period within which the values will be unzipped
     * @return
     */
    protected SortedMap<LocalDate, BigDecimal> unzip(SortedMap<LocalDate, BigDecimal> values,
                                                     Period period) {
        SortedMap<LocalDate, BigDecimal> unzipVals = Maps.newTreeMap();
        for (Map.Entry<LocalDate, BigDecimal> entry : values.entrySet()) {
            LocalDate date = entry.getKey();
            BigDecimal value = entry.getValue();
            if (period.contains(date)) {
                unzipVals.put(date, value);
            }
        }
        return unzipVals;
    }
}
