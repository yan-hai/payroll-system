package com.nobodyhub.payroll.core.item.hr;

import com.nobodyhub.payroll.core.item.common.Item;

/**
 * [HR] HR item with value from limited options,
 * e.g., values are selected from a dropdown list
 *
 * @author yan_h
 * @since 2018/5/30
 */
public class HrOptionItem extends Item<String, HrOptionItem> {
    public HrOptionItem(String itemId) {
        super(itemId, String.class);
    }

    @Override
    public String defaultValue() {
        return "0";
    }

    @Override
    public HrOptionItem build() {
        return new HrOptionItem(id);
    }
}
