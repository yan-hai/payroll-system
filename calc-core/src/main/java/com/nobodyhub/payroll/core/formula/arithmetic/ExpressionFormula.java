package com.nobodyhub.payroll.core.formula.arithmetic;

import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.formula.common.Formula;
import com.nobodyhub.payroll.core.formula.common.Operator;
import com.nobodyhub.payroll.core.item.ItemContext;
import com.nobodyhub.payroll.core.item.abstr.Item;

import java.math.BigDecimal;

/**
 * A Formula formed by Arithmetic Expressions
 *
 * @author Ryan
 */
public class ExpressionFormula extends Formula {
    private Operator operator;
    private String operandId;
    private ExpressionFormula anotherOperand;

    @Override
    public BigDecimal evaluate(ItemContext context) throws PayrollCoreException {
        Item<BigDecimal> operand = context.get(operandId, BigDecimal.class);
        if (operator == null || anotherOperand == null) {
            return operand.getValue();
        }
        return operator.apply(operand.getValue(), anotherOperand.evaluate(context));
    }
}