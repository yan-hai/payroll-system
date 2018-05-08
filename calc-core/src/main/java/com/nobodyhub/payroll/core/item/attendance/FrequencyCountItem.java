package com.nobodyhub.payroll.core.item.attendance;

import com.nobodyhub.payroll.core.item.common.Item;

import java.math.BigDecimal;

/**
 * [Attendence] Number of times
 *
 * @author yan_h
 * @since 2018-05-04.
 */
public class FrequencyCountItem extends Item<BigDecimal, FrequencyCountItem> {

    public FrequencyCountItem(String itemId) {
        super(itemId, BigDecimal.class);
    }

    @Override
    public void setStringValue(String value) {
        this.value = new BigDecimal(value);
    }

    @Override
    public BigDecimal getDefaultValue() {
        return BigDecimal.ZERO;
    }

    @Override
    public FrequencyCountItem build() {
        return new FrequencyCountItem(itemId);
    }
}
