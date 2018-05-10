package com.nobodyhub.payroll.core.item.calendar;

import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.util.DateFormatUtils;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

import static com.nobodyhub.payroll.core.exception.PayrollCoreExceptionCode.PERIOD_INVALID;

/**
 * @author yan_h
 * @since 2018-05-09.
 */
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class Period implements Comparable<Period> {
    /**
     * The start date(inclusive)
     */
    private final LocalDate start;
    /**
     * The end date(inclusive)
     */
    private final LocalDate end;

    /**
     * Create new Period from start to end
     *
     * @param start start date(inclusive)
     * @param end   end date(inclusive)
     * @return
     * @throws PayrollCoreException if not valid period
     */
    public static Period of(LocalDate start, LocalDate end) throws PayrollCoreException {
        if (end.isBefore(start)) {
            throw new PayrollCoreException(PERIOD_INVALID)
                    .addValue("start", start)
                    .addValue("end", end);
        }
        return new Period(start, end);
    }

    public static Period of(String start, String end) throws PayrollCoreException {
        return of(DateFormatUtils.parseDate(start), DateFormatUtils.parseDate(end));
    }

    public boolean contains(LocalDate date) {
        return date.compareTo(start) >= 0
                && date.compareTo(end) <= 0;
    }

    public boolean overlaps(Period other) {
        return other.equals(this)
                || (start.compareTo(other.end) <= 0 && other.start.compareTo(end) <= 0);
    }

    public boolean isAfter(LocalDate date) {
        return date.isBefore(start);
    }

    public boolean isBefore(LocalDate date) {
        return date.isAfter(end);
    }

    @Override
    public int compareTo(Period o) {
        return this.start.compareTo(o.start);
    }
}
