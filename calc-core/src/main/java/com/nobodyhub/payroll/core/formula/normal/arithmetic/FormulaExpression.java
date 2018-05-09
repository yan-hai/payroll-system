package com.nobodyhub.payroll.core.formula.normal.arithmetic;

import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.formula.common.Operator;
import com.nobodyhub.payroll.core.task.execution.ExecutionContext;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

/**
 * A NormalFormula formed by Arithmetic Expressions
 *
 * @author Ryan
 */
@Getter
public class FormulaExpression {
    private Operator operator;
    private String operandItemId;
    private FormulaExpression anotherOperand;

    public BigDecimal evaluate(ExecutionContext context, LocalDate date) throws PayrollCoreException {
        BigDecimal value = context.get(operandItemId, date, BigDecimal.class);
        if (operator == null || anotherOperand == null) {
            return value;
        }
        return operator.apply(value, anotherOperand.evaluate(context, date));
    }

    public void getRequiredItems(Set<String> itemIds) {
        itemIds.add(operandItemId);
        if (anotherOperand != null) {
            anotherOperand.getRequiredItems(itemIds);
        }
    }
}