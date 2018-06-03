package com.nobodyhub.payroll.core.formula.normal.map;

import com.google.common.collect.Sets;
import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.formula.common.Comparator;
import com.nobodyhub.payroll.core.formula.normal.map.operand.ConditionOperand;
import com.nobodyhub.payroll.core.item.common.Item;
import com.nobodyhub.payroll.core.task.execution.ExecutionContext;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Set;

/**
 * @author yan_h
 * @since 2018-05-04.
 */
@Getter
public class FormulaCondition<T extends Comparable<? super T>> {
    /**
     * The Class of item value
     */
    protected final Class<T> clazz;
    /**
     * Item id
     */
    protected final String itemId;
    /**
     * Comparator for item value and bounds(consist of {@link this#lower} and {@link this#higher})
     */
    protected final Comparator comparator;
    /**
     * the lower bound
     */
    protected final ConditionOperand<T> lower;
    /**
     * the higher bound
     */
    protected final ConditionOperand<T> higher;

    public FormulaCondition(Item<T, ?> item,
                            Comparator comparator,
                            ConditionOperand<T> lower,
                            ConditionOperand<T> higher) {
        this.clazz = item.getValueCls();
        this.itemId = item.getId();
        this.comparator = comparator;
        this.lower = lower;
        this.higher = higher;
    }

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
                getValue(lower, context, date),
                getValue(higher, context, date));
    }

    private T getValue(ConditionOperand<T> operand, ExecutionContext context, LocalDate date) throws PayrollCoreException {
        if (operand == null) {
            return null;
        }
        return operand.getValue(context, date);
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
        if (lower != null) {
            segments.addAll(lower.getDateSegment(context));
        }
        if (higher != null) {
            segments.addAll(higher.getDateSegment(context));
        }
        return segments;
    }

    /**
     * Get the item ids, if any, involved in this condition
     *
     * @return
     */
    public Set<String> getRequireIds() {
        Set<String> itemIds = Sets.newHashSet(itemId);
        if (lower != null && lower.getItemId() != null) {
            itemIds.add(lower.getItemId());
        }
        if (higher != null && higher.getItemId() != null) {
            itemIds.add(higher.getItemId());
        }
        return itemIds;
    }
}
