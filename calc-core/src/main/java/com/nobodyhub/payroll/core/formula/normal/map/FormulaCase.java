package com.nobodyhub.payroll.core.formula.normal.map;

import com.google.common.collect.Lists;
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
     * <p>
     * the smaller, the higer priority
     */
    private final int order;
    /**
     * the restrict conditions
     */
    private final List<FormulaCondition> conditions = Lists.newArrayList();
    /**
     * the corresponding value when this case matches
     */
    @Getter
    private final BigDecimal value;

    /**
     * Check whether this case matches or not
     * <p>
     * Short-circuit evaluation applied.
     *
     * @param context
     * @param date
     * @return
     * @throws PayrollCoreException
     */
    public boolean matches(ExecutionContext context, LocalDate date) throws PayrollCoreException {
        for (FormulaCondition condition : conditions) {
            boolean result = condition.matches(context, date);
            if (!result) {
                return false;
            }
        }
        return true;
    }

    /**
     * Get involved item ids
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public Set<String> getRequiredItems() {
        Set<String> itemIds = Sets.newHashSet();
        for (FormulaCondition condition : conditions) {
            itemIds.addAll(condition.getRequireIds());
        }
        return itemIds;
    }

    /**
     * Get date segments
     *
     * @param context
     * @return
     * @throws PayrollCoreException
     */
    @SuppressWarnings("unchecked")
    public Set<LocalDate> getDateSegment(ExecutionContext context) throws PayrollCoreException {
        Set<LocalDate> dateSet = Sets.newHashSet();
        for (FormulaCondition condition : conditions) {
            dateSet.addAll(condition.getDateSegment(context));
        }
        return dateSet;
    }

    /**
     * add condition
     *
     * @param condition
     */
    public void addCondition(FormulaCondition condition) {
        this.conditions.add(condition);
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