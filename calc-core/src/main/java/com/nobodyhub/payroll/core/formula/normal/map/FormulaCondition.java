package com.nobodyhub.payroll.core.formula.normal.map;

import com.google.common.collect.Sets;
import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.formula.common.Comparator;
import com.nobodyhub.payroll.core.formula.normal.map.operand.ConditionOperand;
import com.nobodyhub.payroll.core.task.execution.ExecutionContext;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Set;

/**
 * @author yan_h
 * @since 2018-05-04.
 */
@Getter
public abstract class FormulaCondition<T extends Comparable<? super T>> {
    /**
     * The Class of item value
     */
    protected Class<T> clazz;
    /**
     * Item id
     */
    protected String itemId;
    /**
     * Comparator for item value and bounds(consist of {@link this#lower} and {@link this#higher})
     */
    protected Comparator comparator;
    /**
     * the lower bound
     */
    protected ConditionOperand<T> lower;
    /**
     * the higher bound
     */
    protected ConditionOperand<T> higher;

    /**
     * Check whether the item value on given date matcher the given bound or not
     *
     * @param context
     * @param date
     * @return
     * @throws PayrollCoreException
     */
    public boolean matches(ExecutionContext context, LocalDate date) throws PayrollCoreException {
        return comparator.apply(context.getItemValue(itemId, date, clazz),
                lower.getValue(context, date),
                higher.getValue(context, date));
    }

    /**
     * Get the date segment in the period
     *
     * @param context
     * @return
     * @throws PayrollCoreException
     */
    @SuppressWarnings("unchecked")
    public Set<LocalDate> getDateSegment(ExecutionContext context) throws PayrollCoreException {
        Set<LocalDate> segments = context.get(itemId).getDateSegment();
        segments.addAll(lower.getDateSegment(context));
        segments.addAll(higher.getDateSegment(context));
        return segments;
    }

    /**
     * Get the item ids, if any, involved in this condition
     *
     * @return
     */
    public Set<String> getRequireIds() {
        Set<String> itemIds = Sets.newHashSet(itemId);
        if (lower.getItemId() != null) {
            itemIds.add(lower.getItemId());
        }
        if (higher.getItemId() != null) {
            itemIds.add(higher.getItemId());
        }
        return itemIds;
    }
}
