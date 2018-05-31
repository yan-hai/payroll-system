package com.nobodyhub.payroll.core.item.calendar;

import com.nobodyhub.payroll.core.exception.PayrollCoreException;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A count the non-zero values in the {@link this#values}
 *
 * @author yan_h
 * @since 2018/5/31
 */
public class CalendarCountItem extends CalendarItem {
    public CalendarCountItem(String itemId) {
        super(itemId);
    }

    @Override
    public CalendarCountItem build() {
        return new CalendarCountItem(id);
    }

    @Override
    public BigDecimal getValue(LocalDate date) throws PayrollCoreException {
        return new BigDecimal(getValues().values()
                .stream()
                .filter((value) -> BigDecimal.ZERO.compareTo(value) != 0)
                .count());
    }
}
