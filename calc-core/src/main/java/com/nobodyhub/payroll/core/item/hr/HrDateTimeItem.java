package com.nobodyhub.payroll.core.item.hr;

import com.nobodyhub.payroll.core.item.common.Item;
import com.nobodyhub.payroll.core.util.DateFormatUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * [HR] HR item with DateTime value
 *
 * @author yan_h
 * @since 2018-05-04.
 */
public class HrDateTimeItem extends Item<LocalDateTime, HrDateTimeItem> {

    public HrDateTimeItem(String itemId) {
        super(itemId);
    }

    @Override
    public void addAsString(LocalDate date, String value) {
        this.values.put(date, DateFormatUtils.parseDateTime(value));
    }

    @Override
    public String getValueAsString(LocalDate date) {
        return DateFormatUtils.convertDate(getValue(date));
    }

    @Override
    public LocalDateTime defaultValue() {
        return LocalDateTime.now();
    }

    @Override
    public HrDateTimeItem build() {
        return new HrDateTimeItem(itemId);
    }
}
