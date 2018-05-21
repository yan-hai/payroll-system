package com.nobodyhub.payroll.core.proration.abstr;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.nobodyhub.payroll.core.common.Identifiable;
import com.nobodyhub.payroll.core.common.Period;
import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.task.execution.ExecutionContext;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

/**
 * Prorate the values based on the related calendar item
 *
 * @author yan_h
 * @since 2018-05-10.
 */
@Getter
@RequiredArgsConstructor
public abstract class Proration implements Identifiable {
    /**
     * id of this proration
     */
    protected final String id;
    /**
     * the id of related calendar item
     */
    protected final String calendarItemId;

    /**
     * Prorate the given values into a final value
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
     * the final periods are all within the given inclusive period
     *
     * @param beforeValues value list
     * @param period       the inclusive period
     * @return
     * @throws PayrollCoreException
     */
    protected SortedMap<Period, BigDecimal> convertValueToPeriod(Map<LocalDate, BigDecimal> beforeValues,
                                                                 Period period) throws PayrollCoreException {
        List<Period> periods = convertDateToPeriod(beforeValues.keySet(), period);
        SortedMap<Period, BigDecimal> result = Maps.newTreeMap();
        for (Period sub : periods) {
            result.put(sub, beforeValues.get(sub.getStart()));
        }
        return result;
    }

    /**
     * convert a set of LocalDates within the given period into a Period list
     *
     * @param dates  date list
     * @param period the inclusive period
     * @return
     * @throws PayrollCoreException
     */
    protected List<Period> convertDateToPeriod(Set<LocalDate> dates,
                                               Period period) throws PayrollCoreException {
        Set<LocalDate> dateWithinPeriod = Sets.newHashSet();
        for (LocalDate date : dates) {
            if (period.isAfter(date)) {
                // before
                dateWithinPeriod.add(period.getStart());
            } else if (period.contains(date)) {
                // in between
                dateWithinPeriod.add(date);
            } else {
                // after
                continue;
            }
        }
        dateWithinPeriod.add(period.getEnd());

        List<LocalDate> dateList = Lists.newArrayList(dateWithinPeriod);
        Collections.sort(dateList);
        List<Period> periods = Lists.newArrayList();
        for (int idx = 0; idx < dateList.size() - 1; idx++) {
            if (idx < dateList.size() - 2) {
                periods.add(Period.of(dateList.get(idx), dateList.get(idx + 1).minusDays(1), null));
            } else {
                periods.add(Period.of(dateList.get(idx), dateList.get(idx + 1), null));
            }
        }
        return periods;
    }

}
