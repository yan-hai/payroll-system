package com.nobodyhub.payroll.core.item.common;

/**
 * Interface for the Builder of {@link Item}
 *
 * @author Ryan
 */
public interface ItemBuilder<IT> {
    /**
     * create a new item instance
     *
     * @return
     */
    IT build();
}
