package com.nobodyhub.payroll.core.item.hr;

import com.nobodyhub.payroll.core.item.common.Item;
import com.nobodyhub.payroll.core.util.DateFormatUtils;

import java.time.LocalDate;

/**
 * [HR] HR item with Date value
 *
 * @author yan_h
 * @since 2018-05-04.
 */
public class HrDateItem extends Item<LocalDate, HrDateItem> {

    public HrDateItem(String itemId) {
        super(itemId);
    }

    @Override
    public String getValueAsString(LocalDate date) {
        return DateFormatUtils.convertDate(getValue(date));
    }

    @Override
    public void addAsString(LocalDate date, String value) {
        this.values.put(date, DateFormatUtils.parseDate(value));
    }

    @Override
    public LocalDate defaultValue() {
        return LocalDate.now();
    }

    @Override
    public HrDateItem build() {
        return new HrDateItem(itemId);
    }
}
