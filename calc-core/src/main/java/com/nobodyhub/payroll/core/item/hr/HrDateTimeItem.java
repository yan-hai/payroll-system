package com.nobodyhub.payroll.core.item.hr;

import com.nobodyhub.payroll.core.abstr.Item;

import java.time.LocalDateTime;

/**
 * Date&Time HR item
 *
 * @author yan_h
 * @since 2018-05-04.
 */
public class HrDateTimeItem extends Item<LocalDateTime> {

    public HrDateTimeItem(String itemId, String itemName) {
        super(itemId, itemName);
    }
}
