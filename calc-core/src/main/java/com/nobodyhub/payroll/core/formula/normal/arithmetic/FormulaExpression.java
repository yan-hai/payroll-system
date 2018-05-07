package com.nobodyhub.payroll.core.formula.normal.arithmetic;

import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.formula.common.Operator;
import com.nobodyhub.payroll.core.item.common.Item;
import com.nobodyhub.payroll.core.task.ExecutionContext;

import java.math.BigDecimal;
import java.util.Set;

/**
 * A NormalFormula formed by Arithmetic Expressions
 *
 * @author Ryan
 */
public class FormulaExpression {
    private Operator operator;
    private String operandId;
    private FormulaExpression anotherOperand;

    @SuppressWarnings("unchecked")
    public BigDecimal evaluate(ExecutionContext context) throws PayrollCoreException {
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