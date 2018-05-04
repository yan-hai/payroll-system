package com.nobodyhub.payroll.core.abstr;

import com.nobodyhub.payroll.core.common.ItemComparator;
import com.nobodyhub.payroll.core.exception.PayrollCoreException;

/**
 * @author yan_h
 * @since 2018-05-04.
 */
public abstract class FormulaCondition<T extends Comparable<T>> {
    protected Item<T> item;
    protected ItemComparator comparator;
    protected T lower;
    protected T higher;

    public boolean evaluate() throws PayrollCoreException {
        return comparator.apply(item, lower, higher);
    }
}
