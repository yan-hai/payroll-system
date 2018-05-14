package com.nobodyhub.payroll.core.item.common;

import com.nobodyhub.payroll.core.common.Identifiable;

/**
 * Interface for the Builder of {@link Item}
 *
 * @author Ryan
 */
public interface ItemBuilder<IT> extends Identifiable {
    /**
     * create a new item instance
     *
     * @return new item instance
     */
    IT build();

    /**
     * get the id of the item to be created
     *
     * @return id of instance to be created
     */
    @Override
    String getId();
}
