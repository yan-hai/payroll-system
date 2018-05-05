package com.nobodyhub.payroll.core.formula.arithmetic;

import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.formula.common.Formula;
import com.nobodyhub.payroll.core.formula.common.Operator;
import com.nobodyhub.payroll.core.item.abstr.Item;

import java.math.BigDecimal;

/**
 * A
 *
 * @author Ryan
 */
public class ArithmeticFormula extends Formula {
    private Operator operator;
    private Item<BigDecimal> operand;
    private ArithmeticFormula anotherOperand;

    @Override
    public BigDecimal evaluate() throws PayrollCoreException {
        return operator.apply(operand.getValue(), anotherOperand.evaluate());
    }
}