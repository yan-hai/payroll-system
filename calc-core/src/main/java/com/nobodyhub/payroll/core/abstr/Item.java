package com.nobodyhub.payroll.core.abstr;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Base class for all kinds of items used in payroll system
 *
 * @author yan_h
 * @since 2018-05-04.
 */
@Getter
@RequiredArgsConstructor
public abstract class Item<T> {
    /**
     * Unique item id
     */
    protected final String itemId;
    /**
     * Item name
     */
    protected final String itemName;
    /**
     * Raw value of item
     */
    protected T value;
}
