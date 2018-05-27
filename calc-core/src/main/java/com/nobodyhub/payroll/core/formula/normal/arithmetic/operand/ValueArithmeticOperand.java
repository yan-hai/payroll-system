package com.nobodyhub.payroll.core.formula.normal.arithmetic.operand;

import com.nobodyhub.payroll.core.formula.common.operand.ValueOperand;
import com.nobodyhub.payroll.core.formula.normal.arithmetic.operand.abstr.ArithmeticOperand;

import java.math.BigDecimal;

/**
 * The operand that contains a decimal value
 *
 * @author yan_h
 * @since 2018-05-10
 */
public class ValueArithmeticOperand extends ValueOperand<BigDecimal> implements ArithmeticOperand {

    public ValueArithmeticOperand(BigDecimal value) {
        super(value);
    }

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
}
