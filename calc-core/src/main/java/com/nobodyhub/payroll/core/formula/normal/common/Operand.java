package com.nobodyhub.payroll.core.formula.normal.common;

import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.task.execution.ExecutionContext;

import java.time.LocalDate;
import java.util.Set;

/**
 * Operand for Formula
 *
 * @author yan_h
 * @since 27/05/2018
 */
public interface Operand<T extends Comparable<T>> {
    /**
     * Return the value of the operand
     *
     * @param context context to get value
     * @param date    refer date
     * @return
     * @throws PayrollCoreException
     */
    T getValue(ExecutionContext context, LocalDate date) throws PayrollCoreException;

    /**
     * get item id related with this ArithmeticOperand,
     *
     * @return null if not related item
     */
    String getItemId();

    /**
     * get the split date of all date range of this values
     *
     * @param context
     * @return
     * @throws PayrollCoreException
     */
    Set<LocalDate> getDateSegment(ExecutionContext context) throws PayrollCoreException;
}
