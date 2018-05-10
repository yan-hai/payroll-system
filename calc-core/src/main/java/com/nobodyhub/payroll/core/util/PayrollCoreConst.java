package com.nobodyhub.payroll.core.util;

import com.nobodyhub.payroll.core.item.payment.PaymentItem;

import java.math.MathContext;
import java.time.LocalDate;

import static java.math.MathContext.DECIMAL128;

/**
 * @author yan_h
 * @since 2018-05-08.
 */
public final class PayrollCoreConst {
    private PayrollCoreConst() {
    }

    /**
     * the rounding rule for intermediate calculation,
     * the actual for each item rounding rule will be decided by {@link PaymentItem#roundingRule}
     */
    public static final MathContext MATH_CONTEXT = DECIMAL128;

    public static final LocalDate END_OF_TIME = LocalDate.of(2382, 12, 31);
}
