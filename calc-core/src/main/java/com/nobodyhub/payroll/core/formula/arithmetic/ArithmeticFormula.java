package com.nobodyhub.payroll.core.formula.arithmetic;

import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.formula.common.Formula;
import com.nobodyhub.payroll.core.item.ItemContext;

import java.math.BigDecimal;

/**
 * A Formula formed by Arithmetic Expressions
 *
 * @author Ryan
 */
public class ArithmeticFormula extends Formula {

    private String itemId;
    private FormulaExpression expression;

    @Override
    public BigDecimal evaluate(ItemContext context) throws PayrollCoreException {
        BigDecimal result = expression.evaluate(context);
        context.add(itemId, result);
        return result;
    }
}