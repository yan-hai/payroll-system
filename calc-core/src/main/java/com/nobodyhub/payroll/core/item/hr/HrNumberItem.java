package com.nobodyhub.payroll.core.item.hr;

import com.nobodyhub.payroll.core.item.abstr.Item;

import java.math.BigDecimal;

/**
 * Number HR item
 *
 * @author yan_h
 * @since 2018-05-04.
 */
public class HrNumberItem extends Item<BigDecimal, HrNumberItem> {

    public HrNumberItem(String itemId) {
        super(itemId);
    }

    @Override
    public BigDecimal getDefaultValue() {
        return BigDecimal.ZERO;
    }

    @Override
    public HrNumberItem build() {
        return new HrNumberItem(itemId);
    }
}
