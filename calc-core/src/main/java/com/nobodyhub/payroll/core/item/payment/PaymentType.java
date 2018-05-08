package com.nobodyhub.payroll.core.item.payment;

import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.util.PayrollCoreConst;

import java.math.BigDecimal;

import static com.nobodyhub.payroll.core.exception.PayrollCoreExceptionCode.PAYMENTTYPE_UNIMPLEMENTED;

/**
 * The type of payment items
 *
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

    public BigDecimal aggregate(BigDecimal value, BigDecimal aggregateValue) throws PayrollCoreException {
        switch (this) {
            case ALLOWANCE: {
                return aggregateValue.add(value, PayrollCoreConst.MATH_CONTEXT);
            }
            case DEDUCTION: {
                return aggregateValue.subtract(value, PayrollCoreConst.MATH_CONTEXT);
            }
            default: {
                throw new PayrollCoreException(PAYMENTTYPE_UNIMPLEMENTED);
            }
        }
    }
}
