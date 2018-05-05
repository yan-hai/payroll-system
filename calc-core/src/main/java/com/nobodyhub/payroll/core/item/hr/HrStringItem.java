package com.nobodyhub.payroll.core.item.hr;

import com.nobodyhub.payroll.core.item.abstr.Item;

/**
 * String HR item
 *
 * @author yan_h
 * @since 2018-05-04.
 */
public class HrStringItem extends Item<String, HrStringItem> {

    public HrStringItem(String itemId) {
        super(itemId);
    }

    @Override
    public String getDefaultValue() {
        return "";
    }

    @Override
    public HrStringItem build() {
        return new HrStringItem(itemId);
    }
}
