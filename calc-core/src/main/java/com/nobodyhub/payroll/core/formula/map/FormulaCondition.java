package com.nobodyhub.payroll.core.formula.map;

import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.formula.common.ItemComparator;
import com.nobodyhub.payroll.core.item.abstr.Item;

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
