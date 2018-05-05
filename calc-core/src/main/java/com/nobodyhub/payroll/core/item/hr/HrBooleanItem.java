package com.nobodyhub.payroll.core.item.hr;

import com.nobodyhub.payroll.core.item.abstr.Item;

/**
 * Boolean HR item
 *
 * @author yan_h
 * @since 2018-05-04.
 */
public class HrBooleanItem extends Item<Boolean, HrBooleanItem> {

    public HrBooleanItem(String itemId) {
        super(itemId);
    }

    @Override
    public Boolean getDefaultValue() {
        return false;
    }

    @Override
    public HrBooleanItem build() {
        return new HrBooleanItem(itemId);
    }
}
