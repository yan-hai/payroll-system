package com.nobodyhub.payroll.core.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.nobodyhub.payroll.core.common.Period;
import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author yan_h
 * @since 23/05/2018
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PeriodUtils {
    /**
     * convert a set of LocalDates within the given period into a Period list
     *
     * @param dates  date list
     * @param period the inclusive period
     * @return
     * @throws PayrollCoreException
     */
    public static final List<Period> convertDateToPeriod(Set<LocalDate> dates,
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
