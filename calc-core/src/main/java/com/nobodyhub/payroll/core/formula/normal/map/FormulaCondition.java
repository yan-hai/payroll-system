package com.nobodyhub.payroll.core.formula.normal.map;

import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.formula.common.Comparator;
import com.nobodyhub.payroll.core.task.execution.ExecutionContext;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Set;

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

    public boolean matches(ExecutionContext context, LocalDate date) throws PayrollCoreException {
        return comparator.apply(context.getItemValue(itemId, date, clazz), lower, higher);
    }

    public Set<LocalDate> getDateSplit(ExecutionContext context) throws PayrollCoreException {
        return context.get(itemId).getDateSplit();
    }
}
