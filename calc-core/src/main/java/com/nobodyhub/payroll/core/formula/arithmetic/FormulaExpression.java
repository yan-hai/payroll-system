package com.nobodyhub.payroll.core.formula.arithmetic;

import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.formula.common.Operator;
import com.nobodyhub.payroll.core.item.ItemContext;
import com.nobodyhub.payroll.core.item.abstr.Item;

import java.math.BigDecimal;
import java.util.Set;

/**
 * A Formula formed by Arithmetic Expressions
 *
 * @author Ryan
 */
public class FormulaExpression {
    private Operator operator;
    private String operandId;
    private FormulaExpression anotherOperand;

    @SuppressWarnings("unchecked")
    public BigDecimal evaluate(ItemContext context) throws PayrollCoreException {
        Item<BigDecimal, ?> operand = context.get(operandId);
        if (operator == null || anotherOperand == null) {
            return operand.getValue();
        }
        return operator.apply(context.getMathContext(), operand.getValue(), anotherOperand.evaluate(context));
    }

    public void getRequiredItems(Set<String> itemIds) {
        itemIds.add(operandId);
        if (anotherOperand != null) {
            anotherOperand.getRequiredItems(itemIds);
        }
    }
}