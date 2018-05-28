package com.nobodyhub.payroll.core.formula.normal.map.operand;

import com.nobodyhub.payroll.core.formula.common.operand.ValueOperand;

/**
 * Value operand which contains a single value
 *
 * @author yan_h
 * @since 28/05/2018
 */
public class ValueConditionOperand<T extends Comparable<? super T>> extends ValueOperand<T> implements ConditionOperand<T> {
    private ValueConditionOperand(T value) {
        super(value);
    }

    public static <T extends Comparable<? super T>> ValueConditionOperand<T> of(T value) {
        return new ValueConditionOperand<>(value);
    }
}
