package com.nobodyhub.payroll.core.item.attendance;

import com.nobodyhub.payroll.core.item.common.Item;

import java.math.BigDecimal;

/**
 * [Attendence] Number of days
 *
 * @author yan_h
 * @since 2018-05-04.
 */
public class DayCountItem extends Item<BigDecimal, DayCountItem> {

    public DayCountItem(String itemId) {
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
    public DayCountItem build() {
        return new DayCountItem(itemId);
    }
}
