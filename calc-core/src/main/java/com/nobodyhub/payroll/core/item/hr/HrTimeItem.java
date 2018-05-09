package com.nobodyhub.payroll.core.item.hr;

import com.nobodyhub.payroll.core.item.common.Item;
import com.nobodyhub.payroll.core.util.DateFormatUtils;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * [HR] HR item with Time value
 *
 * @author yan_h
 * @since 2018-05-04.
 */
public class HrTimeItem extends Item<LocalTime, HrTimeItem> {

    public HrTimeItem(String itemId) {
        super(itemId);
    }

    @Override
    public void setStringValue(LocalDate date, String value) {
        this.values.put(date, DateFormatUtils.parseTime(value));
    }

    @Override
    public String getValueAsString(LocalDate date) {
        return DateFormatUtils.convertDate(getValue(date));
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
