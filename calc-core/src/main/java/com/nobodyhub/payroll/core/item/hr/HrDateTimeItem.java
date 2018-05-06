package com.nobodyhub.payroll.core.item.hr;

import com.nobodyhub.payroll.core.item.common.Item;

import java.time.LocalDateTime;

/**
 * Date&Time HR item
 *
 * @author yan_h
 * @since 2018-05-04.
 */
public class HrDateTimeItem extends Item<LocalDateTime, HrDateTimeItem> {

    public HrDateTimeItem(String itemId) {
        super(itemId);
    }

    @Override
    public LocalDateTime getDefaultValue() {
        return LocalDateTime.now();
    }

    @Override
    public HrDateTimeItem build() {
        return new HrDateTimeItem(itemId);
    }
}
