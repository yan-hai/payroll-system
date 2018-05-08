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
        super(itemId, LocalDate.class);
    }

    @Override
    public void setStringValue(String value) {
        this.value = DateFormatUtils.parseDate(value);
    }

    @Override
    public String getValueAsString() {
        return DateFormatUtils.convertDate(this.value);
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
