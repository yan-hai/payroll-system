package com.nobodyhub.payroll.core.proration.abstr;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.item.calendar.Period;
import com.nobodyhub.payroll.core.task.execution.ExecutionContext;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;

import static com.nobodyhub.payroll.core.util.PayrollCoreConst.END_OF_TIME;

/**
 * @author yan_h
 * @since 2018-05-10.
 */
@Getter
@RequiredArgsConstructor
public abstract class Proration {
    /**
     * id of this proration
     */
    protected final String prorationId;
    /**
     * the id of related calendar item
     */
    protected final String calendarItemId;

    /**
     * Prorate the given values
     *
     * @param context
     * @param beforeValues
     * @return
     * @throws PayrollCoreException
     */
    public abstract SortedMap<LocalDate, BigDecimal> prorate(ExecutionContext context,
                                                             SortedMap<LocalDate, BigDecimal> beforeValues) throws PayrollCoreException;

    public BigDecimal getFinalValue(ExecutionContext context,
                                    SortedMap<LocalDate, BigDecimal> beforeValues) throws PayrollCoreException {
        SortedMap<LocalDate, BigDecimal> values = prorate(context, beforeValues);
        return values.values().stream().reduce(BigDecimal.ZERO, (a, b) -> (a.add(b)));
    }

    /**
     * convert {@link LocalDate} based map to {@link Period} based map
     *
     * @param beforeValues
     * @return
     * @throws PayrollCoreException
     */
    protected SortedMap<Period, BigDecimal> convertToPeriod(SortedMap<LocalDate, BigDecimal> beforeValues) throws PayrollCoreException {
        List<Period> periods = convertDateToPeriod(beforeValues.keySet());
        SortedMap<Period, BigDecimal> result = Maps.newTreeMap();
        for (Period period : periods) {
            result.put(period, beforeValues.get(period.getStart()));
        }
        return result;
    }

    /**
     * convert a set of LocalDates into a Period list
     *
     * @param dates
     * @return
     * @throws PayrollCoreException
     */
    protected List<Period> convertDateToPeriod(Set<LocalDate> dates) throws PayrollCoreException {
        List<LocalDate> dateList = Lists.newArrayList(dates);
        Collections.sort(dateList);

        List<Period> periods = Lists.newArrayList();
        for (int idx = 0; idx < dateList.size(); idx++) {
            if (idx < dateList.size() - 1) {
                periods.add(Period.of(dateList.get(idx), dateList.get(idx + 1).minusDays(1)));
            } else {
                periods.add(Period.of(dateList.get(idx), END_OF_TIME));
            }
        }
        return periods;
    }
}
