package com.nobodyhub.payroll.core.item.hr;

import com.nobodyhub.payroll.core.item.common.Item;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * [HR] HR item with Numeric(Integer/Decimal) value
 *
 * @author yan_h
 * @since 2018-05-04.
 */
public class HrNumberItem extends Item<BigDecimal, HrNumberItem> {

    public HrNumberItem(String itemId) {
        super(itemId);
    }

    @Override
    public void addAsString(LocalDate date, String value) {
        this.values.put(date, new BigDecimal(value));
    }

    @Override
    public BigDecimal defaultValue() {
        return BigDecimal.ZERO;
    }

    @Override
    public HrNumberItem build() {
        return new HrNumberItem(itemId);
    }
}
