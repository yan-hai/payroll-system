package com.nobodyhub.payroll.core.formula.map;

import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.item.ItemContext;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

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

    public boolean evaluate(ItemContext context) throws PayrollCoreException {
        boolean result = true;
        for (FormulaCondition condition : conditions) {
            result = result && condition.evaluate(context);
        }
        return result;
    }

    @Override
    public int compareTo(FormulaCase o) {
        return this.order - o.order;
    }
}