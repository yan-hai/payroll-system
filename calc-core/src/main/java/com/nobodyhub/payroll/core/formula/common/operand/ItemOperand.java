package com.nobodyhub.payroll.core.formula.common.operand;


import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.formula.common.Function;
import com.nobodyhub.payroll.core.task.execution.ExecutionContext;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

/**
 * @author yan_h
 * @since 27/05/2018
 */
@RequiredArgsConstructor
public abstract class ItemOperand<T extends Comparable<? super T>> implements Operand<T> {
    /**
     * the id of related item
     */
    protected final String itemId;
    /**
     * class of item value
     */
    protected final Class<T> itemValueCls;

    protected final Function function;

    @SuppressWarnings("unchecked")
    @Override
    public T getValue(ExecutionContext context, LocalDate date) throws PayrollCoreException {

        if (function != null
                && itemValueCls == BigDecimal.class
                && context.get(itemId).getValueCls() == BigDecimal.class) {
            return (T) function.apply(context.get(itemId));
        } else {
            return context.getItemValue(itemId, date, itemValueCls);
        }
    }

    @Override
    public String getItemId() {
        return itemId;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Set<LocalDate> getDateSegment(ExecutionContext context) throws PayrollCoreException {
        return context.get(itemId).getDateSegment();
    }
}
