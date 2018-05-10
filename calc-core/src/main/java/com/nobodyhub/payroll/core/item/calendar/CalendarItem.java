package com.nobodyhub.payroll.core.item.calendar;

import com.nobodyhub.payroll.core.item.common.Item;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Calender item for the calculation period
 *
 * @author yan_h
 * @since 2018-05-09.
 */
public class CalendarItem extends Item<BigDecimal, CalendarItem> {

    public CalendarItem(String itemId) {
        super(itemId);
    }

    public BigDecimal getValue(LocalDate start, LocalDate end) {
        BigDecimal value = BigDecimal.ZERO;
        for (LocalDate key : values.keySet()) {
            if (key.compareTo(start) >= 0
                    && key.compareTo(end) <= 0) {
                value = value.add(values.get(key));
            }
        }
        return value;
    }

    @Override
    public void setStringValue(LocalDate date, String value) {
        values.put(date, new BigDecimal(value));
    }

    @Override
    public BigDecimal getDefaultValue() {
        return BigDecimal.ZERO;
    }

    @Override
    public CalendarItem build() {
        return new CalendarItem(itemId);
    }
}
