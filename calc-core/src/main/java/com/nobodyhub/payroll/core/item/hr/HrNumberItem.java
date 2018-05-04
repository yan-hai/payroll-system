package com.nobodyhub.payroll.core.item.hr;

import com.nobodyhub.payroll.core.abstr.Item;

import java.math.BigDecimal;

/**
 * Number HR item
 *
 * @author yan_h
 * @since 2018-05-04.
 */
public class HrNumberItem extends Item<BigDecimal> {

    public HrNumberItem(String itemId, String itemName) {
        super(itemId, itemName);
    }
}
