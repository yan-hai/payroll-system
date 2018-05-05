package com.nobodyhub.payroll.core.item.attendance;

import com.nobodyhub.payroll.core.item.abstr.Item;

import java.math.BigDecimal;

/**
 * Number of times
 *
 * @author yan_h
 * @since 2018-05-04.
 */
public class FrequencyCountItem extends Item<BigDecimal> {

    public FrequencyCountItem(String itemId, String itemName) {
        super(itemId, itemName);
    }
}
