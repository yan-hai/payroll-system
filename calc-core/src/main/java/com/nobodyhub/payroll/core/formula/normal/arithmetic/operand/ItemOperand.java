package com.nobodyhub.payroll.core.formula.normal.arithmetic.operand;

import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.task.execution.ExecutionContext;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

/**
 * @author yan_h
 * @since 2018-05-10
 */
@RequiredArgsConstructor
public class ItemOperand implements Operand {
    private final String itemId;

    @Override
    public BigDecimal getValue(ExecutionContext context, LocalDate date) throws PayrollCoreException {
        return context.getItemValue(itemId, date, BigDecimal.class);
    }

    @Override
    public String getItemId() {
        return itemId;
    }

    @Override
    public Set<LocalDate> getDateSplit(ExecutionContext context) throws PayrollCoreException {
        return context.get(itemId).getDateSplit();
    }
}
