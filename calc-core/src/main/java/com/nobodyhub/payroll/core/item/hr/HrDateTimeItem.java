package com.nobodyhub.payroll.core.item.hr;

import com.nobodyhub.payroll.core.item.common.Item;

import java.time.LocalDateTime;

/**
 * [HR] HR item with DateTime value
 *
 * @author yan_h
 * @since 2018-05-04.
 */
public class HrDateTimeItem extends Item<LocalDateTime, HrDateTimeItem> {

    public HrDateTimeItem(String itemId) {
        super(itemId, LocalDateTime.class);
    }

    @Override
    public LocalDateTime defaultValue() {
        return LocalDateTime.now();
    }

    @Override
    public HrDateTimeItem build() {
        return new HrDateTimeItem(id);
    }
}
