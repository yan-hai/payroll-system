package com.nobodyhub.payroll.core.abstr;

import lombok.Getter;

/**
 * Base class for all kinds of items used in payroll system
 *
 * @author yan_h
 * @since 2018-05-04.
 */
@Getter
public abstract class Item<T> {
    /**
     * Unique item id
     */
    protected String itemId;
    /**
     * Name of item
     */
    protected String itemName;
    /**
     * Raw value of item
     */
    protected T rawValue;
}
