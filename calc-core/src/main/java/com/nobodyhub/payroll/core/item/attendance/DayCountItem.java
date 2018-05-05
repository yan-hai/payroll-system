package com.nobodyhub.payroll.core.item.attendance;

import com.nobodyhub.payroll.core.item.abstr.Item;

import java.math.BigDecimal;

/**
 * Number of days
 *
 * @author yan_h
 * @since 2018-05-04.
 */
public class DayCountItem extends Item<BigDecimal, DayCountItem> {

    public DayCountItem(String itemId) {
        super(itemId);
    }

    @Override
    public BigDecimal getDefaultValue() {
        return BigDecimal.ZERO;
    }

    @Override
    public DayCountItem build() {
        return new DayCountItem(itemId);
    }
}
