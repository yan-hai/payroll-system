package com.nobodyhub.payroll.core.item.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Item stands for the data that are used during the calculation
 *
 * @param <VT> Value Type, type of raw value of this item
 * @param <IT> Item Type, the type of subclass
 * @author yan_h
 * @since 2018-05-04.
 */
@Getter
@RequiredArgsConstructor
public abstract class Item<VT, IT> implements ItemBuilder<IT> {
    /**
     * Unique item id
     */
    protected final String itemId;
    /**
     * Raw value of item
     */
    protected VT value;

    /**
     * set the item's raw value
     *
     * @param value
     */
    public void setValue(VT value) {
        this.value = value;
    }

    /**
     * parse the value from its string form
     *
     * @param value the string form of the value
     */
    public abstract void setStringValue(String value);

    /**
     * return string form of the value
     *
     * @return
     */
    public String getValueAsString() {
        return value.toString();
    }

    /**
     * get the raw value of item
     *
     * @return
     */
    public VT getValue() {
        return value != null ? value : getDefaultValue();
    }

    /**
     * provide default value for the item
     *
     * @return
     */
    public abstract VT getDefaultValue();
}
