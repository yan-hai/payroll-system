package com.nobodyhub.payroll.core.formula.normal.arithmetic.operand;

import com.google.common.collect.Sets;
import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.formula.normal.arithmetic.operand.abstr.ArithmeticOperand;
import com.nobodyhub.payroll.core.task.execution.ExecutionContext;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

/**
 * The operand that contains a decimal value
 *
 * @author yan_h
 * @since 2018-05-10
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ValueArithmeticOperand implements ArithmeticOperand {
    /**
     * the values for the whole period
     */
    private final BigDecimal value;

    /**
     * contruct operand from a {@link BigDecimal}
     *
     * @param value
     * @return
     */
    public static ValueArithmeticOperand of(BigDecimal value) {
        return new ValueArithmeticOperand(value);
    }

    /**
     * contruct operand from a {@link String} that could be parsed to a {@link BigDecimal}
     *
     * @param value
     * @return
     */
    public static ValueArithmeticOperand of(String value) {
        return new ValueArithmeticOperand(new BigDecimal(value));
    }

    @Override
    public BigDecimal getValue(ExecutionContext context, LocalDate date) {
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
