package com.nobodyhub.payroll.core.formula.common;

import com.nobodyhub.payroll.core.exception.PayrollCoreException;

import java.math.BigDecimal;

import static com.nobodyhub.payroll.core.exception.PayrollCoreExceptionCode.OPERATOR_UNIMPLEMENTED;

/**
 * @author Ryan
 */
public enum Operator {
    /**
     * Addition(+)
     */
    ADD,
    /**
     * Subtraction(-)
     */
    SUB,
    /**
     * Multiplication(×)
     */
    MUL,
    /**
     * Division(÷)
     */
    DIV;

    public BigDecimal apply(BigDecimal operand1, BigDecimal operand2) throws PayrollCoreException {
        switch (this) {
            case ADD: {
                return operand1.add(operand2);
            }
            case SUB: {
                return operand1.subtract(operand2);
            }
            case MUL: {
                return operand1.multiply(operand2);
            }
            case DIV: {
                return operand1.divide(operand2);
            }
            default: {
                throw new PayrollCoreException(OPERATOR_UNIMPLEMENTED);
            }
        }
    }
}
