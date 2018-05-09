package com.nobodyhub.payroll.core.formula.common;

import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.item.common.Item;

import static com.nobodyhub.payroll.core.exception.PayrollCoreExceptionCode.COMPARATOR_UNIMPLEMENTED;

/**
 * @author yan_h
 * @since 2018-05-04.
 */
public enum Comparator {
    /**
     * No comparator will be applied
     */
    NA,
    /**
     * Equal
     */
    EQ,
    /**
     * Not equal
     */
    NE,
    /**
     * interval, (start, end)
     */
    INTERVAL_O_O,
    /**
     * interval, (start, end]
     */
    INTERVAL_O_C,
    /**
     * interval, [start, end)
     */
    INTERVAL_C_O,
    /**
     * interval, [start, end]
     */
    INTERVAL_C_C;

    /**
     * Judge whether the item value satifies the Comparation or not
     *
     * @param lower
     * @param higher
     * @param <T>
     * @return
     * @throws PayrollCoreException
     */
    public <T extends Comparable<T>> boolean apply(T itemVal,  T lower, T higher) throws PayrollCoreException {
        switch (this) {
            case NA: {
                return true;
            }
            case EQ: {
                return compare(itemVal, lower) == 0;
            }
            case NE: {
                return compare(itemVal, lower) != 0;
            }
            case INTERVAL_O_O: {
                return (compare(itemVal, lower) > 0) && (compare(itemVal, higher) < 0);
            }
            case INTERVAL_O_C: {
                return (compare(itemVal, lower) > 0) && (compare(itemVal, higher) <= 0);
            }
            case INTERVAL_C_O: {
                return (compare(itemVal, lower) >= 0) && (compare(itemVal, higher) < 0);
            }
            case INTERVAL_C_C: {
                return (compare(itemVal, lower) >= 0) && (compare(itemVal, higher) <= 0);
            }
            default: {
                throw new PayrollCoreException(COMPARATOR_UNIMPLEMENTED);
            }
        }
    }

    protected <T extends Comparable<T>> int compare(T value1, T value2) {
        if (value1 == null) {
            if (value2 == null) {
                return 0;
            } else {
                //null is less than any other
                return -1;
            }
        } else {
            if (value2 == null) {
                //any other is larger than null
                return 1;
            } else {
                return value1.compareTo(value2);
            }
        }
    }
}
