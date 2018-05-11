package com.nobodyhub.payroll.core.item.hr;

import com.nobodyhub.payroll.core.item.common.Item;

/**
 * [HR] HR item with Boolean value
 *
 * @author yan_h
 * @since 2018-05-04.
 */
public class HrBooleanItem extends Item<Boolean, HrBooleanItem> {

    public HrBooleanItem(String itemId) {
        super(itemId, Boolean.class);
    }

    @Override
    public Boolean defaultValue() {
        return false;
    }

    @Override
    public HrBooleanItem build() {
        return new HrBooleanItem(itemId);
    }
}
