package com.nobodyhub.payroll.core.formula.normal.arithmetic;

import com.google.common.collect.Sets;
import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.formula.normal.NormalFormula;
import com.nobodyhub.payroll.core.item.payment.PaymentItem;
import com.nobodyhub.payroll.core.task.execution.context.ExecutionContext;

import java.math.BigDecimal;
import java.util.Set;

/**
 * A NormalFormula formed by Arithmetic Expressions
 *
 * @author Ryan
 */
public class ArithmeticFormula extends NormalFormula {
    private FormulaExpression expression;

    @Override
    public PaymentItem evaluate(ExecutionContext context) throws PayrollCoreException {
        BigDecimal result = expression.evaluate(context);
        return createPaymentItem(result);
    }

    @Override
    public Set<String> getRequiredItems() {
        Set<String> itemIds = Sets.newHashSet();
        expression.getRequiredItems(itemIds);
        return itemIds;
    }
}