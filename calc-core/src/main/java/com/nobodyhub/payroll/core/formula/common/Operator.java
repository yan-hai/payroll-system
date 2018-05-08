package com.nobodyhub.payroll.core.formula.common;

import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.util.PayrollCoreConst;

import java.math.BigDecimal;

import static com.nobodyhub.payroll.core.exception.PayrollCoreExceptionCode.OPERATOR_UNIMPLEMENTED;

/**
 * The arithmetic operations
 *
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

    /**
     * apply the operation, operand1 OP operand2
     *
     * @param operand1
     * @param operand2
     * @return
     * @throws PayrollCoreException
     */
    public BigDecimal apply(BigDecimal operand1, BigDecimal operand2) throws PayrollCoreException {
        switch (this) {
            case ADD: {
                return operand1.add(operand2, PayrollCoreConst.MATH_CONTEXT);
            }
            case SUB: {
                return operand1.subtract(operand2, PayrollCoreConst.MATH_CONTEXT);
            }
            case MUL: {
                return operand1.multiply(operand2, PayrollCoreConst.MATH_CONTEXT);
            }
            case DIV: {
                return operand1.divide(operand2, PayrollCoreConst.MATH_CONTEXT);
            }
            default: {
                throw new PayrollCoreException(OPERATOR_UNIMPLEMENTED);
            }
        }
    }
}
