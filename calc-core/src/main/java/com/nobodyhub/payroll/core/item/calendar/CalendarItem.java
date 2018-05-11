package com.nobodyhub.payroll.core.item.calendar;

import com.nobodyhub.payroll.core.item.common.Item;

import java.math.BigDecimal;

/**
 * Calender item for the calculation period
 *
 * @author yan_h
 * @since 2018-05-09.
 */
public class CalendarItem extends Item<BigDecimal, CalendarItem> {

    public CalendarItem(String itemId) {
        super(itemId, BigDecimal.class);
    }

    @Override
    public BigDecimal defaultValue() {
        return BigDecimal.ZERO;
    }

    @Override
    public CalendarItem build() {
        return new CalendarItem(id);
    }
}
