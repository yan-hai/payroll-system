package com.nobodyhub.payroll.core.item.hr;

import com.nobodyhub.payroll.core.item.common.Item;

import java.time.LocalTime;

/**
 * Date&Time HR item
 *
 * @author yan_h
 * @since 2018-05-04.
 */
public class HrTimeItem extends Item<LocalTime, HrTimeItem> {

    public HrTimeItem(String itemId) {
        super(itemId);
    }

    @Override
    public LocalTime getDefaultValue() {
        return LocalTime.now();
    }

    @Override
    public HrTimeItem build() {
        return new HrTimeItem(itemId);
    }
}
