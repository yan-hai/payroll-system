package com.nobodyhub.payroll.core.item.payment;

import com.nobodyhub.payroll.core.exception.PayrollCoreException;

import java.math.BigDecimal;
import java.math.MathContext;

import static com.nobodyhub.payroll.core.exception.PayrollCoreExceptionCode.PAYMENTTYPE_UNIMPLEMENTED;

/**
 * @author yan_h
 * @since 2018-05-04.
 */
public enum PaymentType {
    /**
     * Allowance(+)
     */
    ALLOWANCE,
    /**
     * Deduction(-)
     */
    DEDUCTION;

    public BigDecimal aggregate(MathContext mathContext, BigDecimal value, BigDecimal aggregateValue) throws PayrollCoreException {
        switch (this) {
            case ALLOWANCE: {
                return aggregateValue.add(value, mathContext);
            }
            case DEDUCTION: {
                return aggregateValue.add(value.negate(), mathContext);
            }
            default: {
                throw new PayrollCoreException(PAYMENTTYPE_UNIMPLEMENTED);
            }
        }
    }
}
