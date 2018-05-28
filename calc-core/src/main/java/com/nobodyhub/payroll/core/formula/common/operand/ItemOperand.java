package com.nobodyhub.payroll.core.formula.common.operand;


import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.task.execution.ExecutionContext;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

/**
 * @author yan_h
 * @since 27/05/2018
 */
@RequiredArgsConstructor
public class ItemOperand<T extends Comparable<? super T>> implements Operand<T> {
    /**
     * the id of related item
     */
    protected final String itemId;
    /**
     * class of item value
     */
    protected final Class<T> itemClz;

    @Override
    public T getValue(ExecutionContext context, LocalDate date) throws PayrollCoreException {
        return context.getItemValue(itemId, date, itemClz);
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
