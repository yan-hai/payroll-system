package com.nobodyhub.payroll.core.formula.common;

import com.nobodyhub.payroll.core.item.payment.PaymentItem;

import java.math.MathContext;

import static java.math.MathContext.DECIMAL128;

/**
 * @author yan_h
 * @since 2018-05-08.
 */
public final class FormulaConst {
    private FormulaConst() {
    }

    /**
     * the rounding rule for intermediate calculation,
     * the actual for each item rounding rule will be decided by {@link PaymentItem#roundingRule}
     */
    public static final MathContext MATH_CONTEXT = DECIMAL128;
}
