package com.nobodyhub.payroll.core.item.hr;

import com.nobodyhub.payroll.core.item.abstr.Item;

import java.time.LocalDate;

/**
 * Date HR item
 *
 * @author yan_h
 * @since 2018-05-04.
 */
public class HrDateItem extends Item<LocalDate> {

    public HrDateItem(String itemId, String itemName) {
        super(itemId, itemName);
    }
}
