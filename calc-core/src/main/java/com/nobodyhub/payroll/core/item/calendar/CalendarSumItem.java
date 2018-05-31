package com.nobodyhub.payroll.core.item.calendar;

import com.nobodyhub.payroll.core.exception.PayrollCoreException;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A sum-up the non-zero values in the {@link this#values}
 *
 * @author yan_h
 * @since 2018/5/31
 */
public class CalendarSumItem extends CalendarItem {
    public CalendarSumItem(String itemId) {
        super(itemId);
    }

    @Override
    public CalendarSumItem build() {
        return new CalendarSumItem(id);
    }

    @Override
    public BigDecimal getValue(LocalDate date) throws PayrollCoreException {
        return getValues().values()
                .stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
