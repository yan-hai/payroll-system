package com.nobodyhub.payroll.core.item.abstr;

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
     * The class of value
     */
    protected Class<T> valueCls;
    /**
     * Raw value of item
     */
    protected T value;

    @SuppressWarnings("unchecked")
    public void setValue(T value) {
        this.value = value;
        this.valueCls = (Class<T>) value.getClass();
    }
}
