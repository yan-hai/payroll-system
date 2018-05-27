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
public abstract class FormulaCondition<T extends Comparable<T>> {
    protected Class<T> clazz;
    protected String itemId;
    protected Comparator comparator;
    protected ConditionOperand<T> lower;
    protected ConditionOperand<T> higher;

    public boolean matches(ExecutionContext context, LocalDate date) throws PayrollCoreException {
        return comparator.apply(context.getItemValue(itemId, date, clazz),
                lower.getValue(context, date),
                higher.getValue(context, date));
    }

    @SuppressWarnings("unchecked")
    public Set<LocalDate> getDateSegment(ExecutionContext context) throws PayrollCoreException {
        Set<LocalDate> segments = context.get(itemId).getDateSegment();
        segments.addAll(lower.getDateSegment(context));
        segments.addAll(higher.getDateSegment(context));
        return segments;
    }

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
