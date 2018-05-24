package com.nobodyhub.payroll.core.formula.normal.arithmetic;

import com.google.common.collect.Sets;
import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.formula.common.Operator;
import com.nobodyhub.payroll.core.formula.normal.arithmetic.operand.abstr.Operand;
import com.nobodyhub.payroll.core.task.execution.ExecutionContext;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

/**
 * A NormalFormula formed by Arithmetic Expressions
 * result = ({@link this#operand}) ({@link this#operator}) ({@link this#anotherOperand})
 * <p>
 * the evaluate of the {@link this#anotherOperand} will perform recursively until either
 * the {@link this#operator} or the {@link this#anotherOperand} is null
 *
 * @author Ryan
 */
@Getter
public class FormulaExpression {
    /**
     * the first operand
     */
    private Operand operand;
    /**
     * the operator
     */
    private Operator operator;
    /**
     * another operand
     */
    private FormulaExpression anotherOperand;

    /**
     * Evaluate the Expression
     *
     * @param context
     * @param date
     * @return
     * @throws PayrollCoreException
     */
    public BigDecimal evaluate(ExecutionContext context, LocalDate date) throws PayrollCoreException {
        BigDecimal value = operand.getValue(context, date);
        if (operator == null || anotherOperand == null) {
            return value;
        }
        return operator.apply(value, anotherOperand.evaluate(context, date));
    }

    /**
     * append the related item ids to the given set
     *
     * @return
     */
    public Set<String> getRequiredItems() {
        Set<String> itemIds = Sets.newHashSet();
        if (operand.getItemId() != null) {
            itemIds.add(operand.getItemId());
        }
        if (anotherOperand != null) {
            itemIds.addAll(anotherOperand.getRequiredItems());
        }
        return itemIds;
    }

    /**
     * get the date segments involved in this expression
     *
     * @param context
     * @return
     * @throws PayrollCoreException
     */
    public Set<LocalDate> getDateSegment(ExecutionContext context) throws PayrollCoreException {
        Set<LocalDate> segments = operand.getDateSegment(context);
        if (anotherOperand != null) {
            segments.addAll(anotherOperand.getDateSegment(context));
        }
        return segments;
    }
}