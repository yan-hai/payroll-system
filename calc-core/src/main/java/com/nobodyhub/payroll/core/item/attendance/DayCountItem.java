package com.nobodyhub.payroll.core.item.attendance;

import com.nobodyhub.payroll.core.abstr.Item;

import java.math.BigDecimal;

/**
 * Number of days
 *
 * @author yan_h
 * @since 2018-05-04.
 */
public class DayCountItem extends Item<BigDecimal> {

    public DayCountItem(String itemId, String itemName) {
        super(itemId, itemName);
    }
}
