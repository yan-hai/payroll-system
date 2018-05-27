package com.nobodyhub.payroll.core.formula.normal.arithmetic.operand;

import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.formula.normal.arithmetic.operand.abstr.ArithmeticOperand;
import com.nobodyhub.payroll.core.item.common.Item;
import com.nobodyhub.payroll.core.task.execution.ExecutionContext;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

/**
 * The operand that contains a {@link Item}
 *
 * @author yan_h
 * @since 2018-05-10
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemArithmeticOperand implements ArithmeticOperand {
    /**
     * the id of related item
     */
    private final String itemId;

    /**
     * contruct operand from a {@link String} itemId
     *
     * @param itemId
     * @return
     */
    public static ItemArithmeticOperand of(String itemId) {
        return new ItemArithmeticOperand(itemId);
    }

    @Override
    public BigDecimal getValue(ExecutionContext context, LocalDate date) throws PayrollCoreException {
        return context.getItemValue(itemId, date, BigDecimal.class);
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
