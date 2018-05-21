package com.nobodyhub.payroll.core.common;

import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.util.DateFormatUtils;
import lombok.*;

import java.time.LocalDate;

import static com.nobodyhub.payroll.core.exception.PayrollCoreExceptionCode.PERIOD_INVALID;

/**
 * A interval formed by start date and end date.
 * Both the start and end date are included in the period
 *
 * @author yan_h
 * @since 2018-05-09.
 */
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@ToString
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
     * The basedate used to get HR information
     * could be null if used {@link this} used only as a interval of date
     */
    private final LocalDate baseDate;

    /**
     * Create new Period from start to end
     *
     * @param start    start date(inclusive)
     * @param end      end date(inclusive)
     * @param baseDate
     * @return
     * @throws PayrollCoreException if not valid period
     */
    public static Period of(LocalDate start, LocalDate end, LocalDate baseDate) throws PayrollCoreException {
        if (end.isBefore(start)) {
            throw new PayrollCoreException(PERIOD_INVALID)
                    .addValue("start", start)
                    .addValue("end", end);
        }
        return new Period(start, end, baseDate);
    }


    /**
     * Create new Period from string representative for date in format
     * {@link DateFormatUtils#DATE_FORMAT}, with null as basedate
     *
     * @param start
     * @param end
     * @return
     * @throws PayrollCoreException
     */
    public static Period of(String start, String end) throws PayrollCoreException {
        return of(DateFormatUtils.parseDate(start), DateFormatUtils.parseDate(end), null);
    }


    /**
     * Create new Period from string representative for date in format {@link DateFormatUtils#DATE_FORMAT}
     *
     * @param start
     * @param end
     * @param baseDate
     * @return
     * @throws PayrollCoreException
     */
    public static Period of(String start, String end, String baseDate) throws PayrollCoreException {
        return of(DateFormatUtils.parseDate(start), DateFormatUtils.parseDate(end), DateFormatUtils.parseDate(baseDate));
    }

    /**
     * whether it contains the given period
     *
     * @param date
     * @return
     */
    public boolean contains(LocalDate date) {
        return date.compareTo(start) >= 0
                && date.compareTo(end) <= 0;
    }

    /**
     * whether it overlap with another period
     *
     * @param other
     * @return
     */
    public boolean overlaps(Period other) {
        return other.equals(this)
                || (start.compareTo(other.end) <= 0 && other.start.compareTo(end) <= 0);
    }

    /**
     * whether this period is after the give {@code date}
     *
     * @param date
     * @return
     */
    public boolean isAfter(LocalDate date) {
        return date.isBefore(start);
    }

    /**
     * whether this period is before the given {@code date}
     *
     * @param date
     * @return
     */
    public boolean isBefore(LocalDate date) {
        return date.isAfter(end);
    }

    /**
     * The periods will be sorted by the order of start date
     * <p>
     * Note: the a.compareTo(b) == 0 does not comply with a.equals(b)
     *
     * @param o
     * @return
     */
    @Override
    public int compareTo(Period o) {
        return this.start.compareTo(o.start);
    }
}
