package com.nobodyhub.payroll.core.item.hr;

import com.nobodyhub.payroll.core.item.common.Item;

import java.math.BigDecimal;

/**
 * [HR] HR item with Numeric(Integer/Decimal) value
 *
 * @author yan_h
 * @since 2018-05-04.
 */
public class HrNumberItem extends Item<BigDecimal, HrNumberItem> {

    public HrNumberItem(String itemId) {
        super(itemId, BigDecimal.class);
    }

    @Override
    public void setStringValue(String value) {
        this.value = new BigDecimal(value);
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
