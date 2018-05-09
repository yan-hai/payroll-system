package com.nobodyhub.payroll.core.item.attendance;

import com.nobodyhub.payroll.core.item.common.Item;

import java.math.BigDecimal;

/**
 * [Attendence] Number of hours
 *
 * @author yan_h
 * @since 2018-05-04.
 */
public class HourCountItem extends Item<BigDecimal, HourCountItem> {

    public HourCountItem(String itemId) {
        super(itemId);
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
    public HourCountItem build() {
        return new HourCountItem(itemId);
    }
}
