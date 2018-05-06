package com.nobodyhub.payroll.core.item.hr;

import com.nobodyhub.payroll.core.item.common.Item;

import java.time.LocalDate;

/**
 * Date HR item
 *
 * @author yan_h
 * @since 2018-05-04.
 */
public class HrDateItem extends Item<LocalDate, HrDateItem> {

    public HrDateItem(String itemId) {
        super(itemId);
    }

    @Override
    public LocalDate getDefaultValue() {
        return LocalDate.now();
    }

    @Override
    public HrDateItem build() {
        return new HrDateItem(itemId);
    }
}
