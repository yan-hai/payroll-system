package com.nobodyhub.payroll.core.formula.common;

import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.item.calendar.CalendarItem;
import com.nobodyhub.payroll.core.item.common.Item;
import com.nobodyhub.payroll.core.util.PayrollCoreConst;

import java.math.BigDecimal;

import static com.nobodyhub.payroll.core.exception.PayrollCoreExceptionCode.FUNCTION_UNIMPLEMENTED;

/**
 * The aggregation function
 *
 * @author yan_h
 * @since 2018/6/3
 */
public enum Function {
    /**
     * Count the number of values.
     * For calendar items, only non-zero items
     */
    COUNT,
    /**
     * Sumup all values
     */
    SUM,
    /**
     * Get the earlist value
     */
    FIRST,
    /**
     * Get the latest value
     */
    LAST,
    /**
     * Get the maximum value
     */
    MAX,
    /**
     * Get the minimum
     */
    MIN,
    /**
     * Get the Arithmetic mean
     */
    AVG;

    public BigDecimal apply(Item<BigDecimal, ?> item) throws PayrollCoreException {
        switch (this) {
            case COUNT: {
                long count = 0L;
                if (item instanceof CalendarItem) {
                    // for calendar items, only count the non-zero values
                    for (BigDecimal value : item.getValues().values()) {
                        if (BigDecimal.ZERO.compareTo(value) != 0) {
                            count++;
                        }
                    }
                } else {
                    count = item.getValues().values().size();
                }
                return BigDecimal.valueOf(count);
            }
            case SUM: {
                BigDecimal total = BigDecimal.ZERO;
                for (BigDecimal value : item.getValues().values()) {
                    total = total.add(value);
                }
                return total;
            }
            case FIRST: {
                return item.getValues().get(item.getValues().firstKey());
            }
            case LAST: {
                return item.getValues().get(item.getValues().lastKey());
            }
            case MIN: {
                BigDecimal minValue = BigDecimal.valueOf(Double.MAX_VALUE);
                for (BigDecimal value : item.getValues().values()) {
                    minValue = minValue.min(value);
                }
                return minValue;
            }
            case MAX: {
                BigDecimal maxValue = BigDecimal.valueOf(Double.MIN_VALUE);
                for (BigDecimal value : item.getValues().values()) {
                    maxValue = maxValue.max(value);
                }
                return maxValue;
            }
            case AVG: {
                BigDecimal totalValue = BigDecimal.ZERO;
                long count = 0;
                for (BigDecimal value : item.getValues().values()) {
                    totalValue = totalValue.add(value);
                    count++;
                }
                if (count == 0) {
                    return BigDecimal.ZERO;
                } else {
                    return totalValue.divide(
                            BigDecimal.valueOf(count),
                            PayrollCoreConst.MATH_CONTEXT
                    );
                }
            }
            default: {
                throw new PayrollCoreException(FUNCTION_UNIMPLEMENTED);
            }
        }
    }
}
