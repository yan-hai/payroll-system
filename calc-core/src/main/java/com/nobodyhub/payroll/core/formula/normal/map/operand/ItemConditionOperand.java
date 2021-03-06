package com.nobodyhub.payroll.core.formula.normal.map.operand;

import com.nobodyhub.payroll.core.formula.common.Function;
import com.nobodyhub.payroll.core.formula.common.operand.ItemOperand;
import com.nobodyhub.payroll.core.item.common.Item;

/**
 * Condition operand which relates to a item, which might have different value within period
 *
 * @author yan_h
 * @since 28/05/2018
 */
public class ItemConditionOperand<T extends Comparable<? super T>>
        extends ItemOperand<T>
        implements ConditionOperand<T> {
    private ItemConditionOperand(
            String itemId,
            Class<T> itemClz,
            Function function) {
        super(itemId, itemClz, function);
    }

    @SuppressWarnings("unchecked")
    public static <T extends Comparable<? super T>> ItemConditionOperand<T> of(
            Item<T, ?> item,
            Function function) {
        return new ItemConditionOperand(item.getId(), item.getValueCls(), function);
    }

    public static <T extends Comparable<? super T>> ItemConditionOperand<T> of(
            Item<T, ?> item) {
        return ItemConditionOperand.of(item, null);
    }

}
