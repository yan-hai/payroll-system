package com.nobodyhub.payroll.core.formula.normal.arithmetic.operand;

import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.task.execution.ExecutionContext;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Mark to be oprerand for Formula
 *
 * @author yan_h
 * @since 2018-05-10
 */
public interface Operand {
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
     * get item id related with this Operand,
     *
     * @return null if not related item
     */
    String getItemId();
}
