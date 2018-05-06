package com.nobodyhub.payroll.core.item.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Base class for all kinds of items used in payroll system
 *
 * @param <VT> value type of the item
 * @param <IT> the type of subclass
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
     * The class of value
     */
    protected final Class<VT> valueCls;
    /**
     * Raw value of item
     */
    protected VT value;

    @SuppressWarnings("unchecked")
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
