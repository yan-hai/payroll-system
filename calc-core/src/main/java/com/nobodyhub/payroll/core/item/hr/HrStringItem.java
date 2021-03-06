package com.nobodyhub.payroll.core.item.hr;

import com.nobodyhub.payroll.core.item.common.Item;

/**
 * [HR] HR item with String value
 *
 * @author yan_h
 * @since 2018-05-04.
 */
public class HrStringItem extends Item<String, HrStringItem> {

    public HrStringItem(String itemId) {
        super(itemId, String.class);
    }

    @Override
    public String defaultValue() {
        return "";
    }

    @Override
    public HrStringItem build() {
        return new HrStringItem(id);
    }
}
