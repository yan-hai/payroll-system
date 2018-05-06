package com.nobodyhub.payroll.core.formula.map;

import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.formula.common.Comparator;
import com.nobodyhub.payroll.core.item.ItemContext;
import com.nobodyhub.payroll.core.item.common.Item;
import lombok.Getter;

/**
 * @author yan_h
 * @since 2018-05-04.
 */
@Getter
public abstract class FormulaCondition<T extends Comparable<T>> {
    protected Class<T> clazz;
    protected String itemId;
    protected Comparator comparator;
    protected T lower;
    protected T higher;

    public boolean evaluate(ItemContext context) throws PayrollCoreException {
        Item<T, ?> item = context.get(itemId);
        return comparator.apply(item, lower, higher);
    }
}
