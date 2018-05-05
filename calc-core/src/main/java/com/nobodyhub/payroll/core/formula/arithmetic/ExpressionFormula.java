package com.nobodyhub.payroll.core.formula.arithmetic;

import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.formula.common.Formula;
import com.nobodyhub.payroll.core.formula.common.Operator;
import com.nobodyhub.payroll.core.item.abstr.Item;

import java.math.BigDecimal;

/**
 * A Formula formed by Arithmetic Expressions
 *
 * @author Ryan
 */
public class ExpressionFormula extends Formula {
    private Operator operator;
    private Item<BigDecimal> operand;
    private ExpressionFormula anotherOperand;

    @Override
    public BigDecimal evaluate() throws PayrollCoreException {
        if (operator == null || anotherOperand == null) {
            return operand.getValue();
        }
        return operator.apply(operand.getValue(), anotherOperand.evaluate());
    }
}