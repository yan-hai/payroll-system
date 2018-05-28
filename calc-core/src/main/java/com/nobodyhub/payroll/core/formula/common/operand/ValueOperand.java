package com.nobodyhub.payroll.core.formula.common.operand;


import com.google.common.collect.Sets;
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
public class ValueOperand<T extends Comparable<? super T>> implements Operand<T> {
    /**
     * the values for the whole period
     */
    protected final T value;

    @Override
    public T getValue(ExecutionContext context, LocalDate date) {
        return value;
    }

    @Override
    public String getItemId() {
        return null;
    }

    @Override
    public Set<LocalDate> getDateSegment(ExecutionContext context) throws PayrollCoreException {
        return Sets.newHashSet(context.getPeriod().getStart());
    }
}
