package com.nobodyhub.payroll.core.item.attendance;

import com.nobodyhub.payroll.core.item.abstr.Item;

import java.math.BigDecimal;

/**
 * Number of hours
 *
 * @author yan_h
 * @since 2018-05-04.
 */
public class HourCountItem extends Item<BigDecimal, HourCountItem> {

    public HourCountItem(String itemId) {
        super(itemId);
    }

    @Override
    public BigDecimal getDefaultValue() {
        return BigDecimal.ZERO;
    }

    @Override
    public HourCountItem build() {
        return new HourCountItem(itemId);
    }
}
