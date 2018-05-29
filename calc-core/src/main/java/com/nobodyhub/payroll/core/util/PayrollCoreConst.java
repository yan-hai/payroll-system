package com.nobodyhub.payroll.core.util;

import com.nobodyhub.payroll.core.item.payment.PaymentItem;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.MathContext;
import java.time.LocalDate;

import static java.math.MathContext.DECIMAL128;

/**
 * @author yan_h
 * @since 2018-05-08.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PayrollCoreConst {
    /**
     * the rounding rule for intermediate calculation,
     * the actual for each item rounding rule will be decided by {@link PaymentItem#roundingRule}
     */
    public static final MathContext MATH_CONTEXT = DECIMAL128;

    /**
     * A very late date
     */
    public static final LocalDate END_OF_TIME = LocalDate.of(2382, 12, 31);

    /**
     * Default port of server
     */
    public static final int DEFAULT_PORT = 9101;
}
