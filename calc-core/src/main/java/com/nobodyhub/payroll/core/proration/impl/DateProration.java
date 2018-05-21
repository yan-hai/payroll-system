package com.nobodyhub.payroll.core.proration.impl;

import com.nobodyhub.payroll.core.common.Period;
import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.item.calendar.CalendarItem;
import com.nobodyhub.payroll.core.item.hr.HrDateItem;
import com.nobodyhub.payroll.core.task.execution.ExecutionContext;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.SortedMap;

/**
 * @author Ryan
 */
public abstract class DateProration extends CalendarProration {
    /**
     * id of related {@link HrDateItem}
     * which stands for start or end date of effective period, inclusive
     */
    protected final String hrDateItemId;

    public DateProration(String prorationId, String calendarItemId, String hrDateItemId) {
        super(prorationId, calendarItemId);
        this.hrDateItemId = hrDateItemId;
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
     *
     * @param calendarItem calendar item includes calendar info
     * @param hrDateItem   HR date item include the effective date
     * @param data         payment values
     * @param period       calculation period
     * @return
     * @throws PayrollCoreException
     */
    protected abstract SortedMap<LocalDate, BigDecimal> proratePeriod(CalendarItem calendarItem,
                                                                      HrDateItem hrDateItem,
                                                                      SortedMap<Period, BigDecimal> data,
                                                                      Period period) throws PayrollCoreException;
}
