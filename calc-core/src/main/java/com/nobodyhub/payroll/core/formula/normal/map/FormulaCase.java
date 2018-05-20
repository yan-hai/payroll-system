package com.nobodyhub.payroll.core.formula.normal.map;

import com.google.common.collect.Sets;
import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.task.execution.ExecutionContext;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
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
@EqualsAndHashCode
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

    public boolean matches(ExecutionContext context, LocalDate date) throws PayrollCoreException {
        boolean result = true;
        for (FormulaCondition condition : conditions) {
            result = result && condition.matches(context, date);
        }
        return result;
    }

    public void getRequiredItems(Set<String> itemIds) {
        for (FormulaCondition condition : conditions) {
            itemIds.add(condition.getItemId());
        }
    }

    public Set<LocalDate> getDateSplit(ExecutionContext context) throws PayrollCoreException {
        Set<LocalDate> dateSet = Sets.newHashSet();
        for (FormulaCondition condition : conditions) {
            dateSet.addAll(condition.getDateSplit(context));
        }
        return dateSet;
    }

    /**
     * Note: this class has a natural ordering that is inconsistent with equals.
     *
     * @param o
     * @return
     */
    @Override
    public int compareTo(FormulaCase o) {
        return this.order - o.order;
    }
}