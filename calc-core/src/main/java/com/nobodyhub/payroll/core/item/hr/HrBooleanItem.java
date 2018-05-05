package com.nobodyhub.payroll.core.item.hr;

import com.nobodyhub.payroll.core.item.abstr.Item;

/**
 * Boolean HR item
 *
 * @author yan_h
 * @since 2018-05-04.
 */
public class HrBooleanItem extends Item<Boolean> {

    public HrBooleanItem(String itemId, String itemName) {
        super(itemId, itemName);
    }

    @Override
    public Boolean getDefaultValue() {
        return false;
    }
}
