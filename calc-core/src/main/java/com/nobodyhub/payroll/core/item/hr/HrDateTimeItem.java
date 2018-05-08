package com.nobodyhub.payroll.core.item.hr;

import com.nobodyhub.payroll.core.item.common.Item;
import com.nobodyhub.payroll.core.util.DateFormatUtils;

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
    public void setStringValue(String value) {
        this.value = DateFormatUtils.parseDateTime(value);
    }

    @Override
    public String getValueAsString() {
        return DateFormatUtils.convertDate(this.value);
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
