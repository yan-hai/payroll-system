package com.nobodyhub.payroll.core.formula.normal.map;

import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.task.execution.context.ExecutionContext;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

/**
 * Each CASE of a {@link MapFormula}
 * <p>
 * Conditions are evaluated using <b>AND</b> operator
 *
 * @author yan_h
 * @since 2018-05-04.
 */
@RequiredArgsConstructor
public class FormulaCase implements Comparable<FormulaCase> {
    /**
     * the index of the case
     */
    private final int order;
    /**
     * the restrict conditions
     */
    private final List<FormulaCondition> conditions;
    /**
     * the corresponding value when this case matches
     */
    @Getter
    private final BigDecimal value;

    public boolean evaluate(ExecutionContext context) throws PayrollCoreException {
        boolean result = true;
        for (FormulaCondition condition : conditions) {
            result = result && condition.evaluate(context);
        }
        return result;
    }

    public void getRequiredItems(Set<String> itemIds) {
        for (FormulaCondition condition : conditions) {
            itemIds.add(condition.getItemId());
        }
    }

    @Override
    public int compareTo(FormulaCase o) {
        return this.order - o.order;
    }
}