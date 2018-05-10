package com.nobodyhub.payroll.core.formula.normal.arithmetic.operand;

import com.nobodyhub.payroll.core.task.execution.ExecutionContext;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author yan_h
 * @since 2018-05-10
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class RawValue implements Operand {
    private final BigDecimal value;

    public static RawValue of(BigDecimal value) {
        return new RawValue(value);
    }

    public static RawValue of(String value) {
        return new RawValue(new BigDecimal(value));
    }

    @Override
    public BigDecimal getValue(ExecutionContext context, LocalDate date) {
        return value;
    }

    @Override
    public String getItemId() {
        return null;
    }
}
