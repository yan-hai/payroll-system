package com.nobodyhub.payroll.core.item.attendance;

import com.nobodyhub.payroll.core.item.abstr.Item;

import java.math.BigDecimal;

/**
 * Number of hours
 *
 * @author yan_h
 * @since 2018-05-04.
 */
public class HourCountItem extends Item<BigDecimal> {

    public HourCountItem(String itemId, String itemName) {
        super(itemId, itemName);
    }

    @Override
    public BigDecimal getDefaultValue() {
        return BigDecimal.ZERO;
    }

}
