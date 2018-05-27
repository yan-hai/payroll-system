package com.nobodyhub.payroll.core.formula.normal.arithmetic.operand.abstr;

import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.task.execution.ExecutionContext;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

/**
 * Mark to be oprerand for Formula
 *
 * @author yan_h
 * @since 2018-05-10
 */
public interface ArithmeticOperand {
    /**
     * Return the value of the operand
     *
     * @param context context to get value
     * @param date    refer date
     * @return
     * @throws PayrollCoreException
     */
    BigDecimal getValue(ExecutionContext context, LocalDate date) throws PayrollCoreException;

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
