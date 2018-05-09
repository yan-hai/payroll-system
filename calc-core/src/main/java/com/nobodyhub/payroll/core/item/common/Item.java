package com.nobodyhub.payroll.core.item.common;

import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.TreeMap;

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
     * values for different period, from the latest to the oldest
     */
    protected TreeMap<LocalDate, VT> values = Maps.newTreeMap((o1, o2) -> (o1.compareTo(o2) * (-1)));

    /**
     * set the item's raw value
     *
     * @param date  start date to be effective
     * @param value
     */
    public void setValue(LocalDate date, VT value) {
        this.values.put(date, value);
    }

    /**
     * parse the value from its string form
     *
     * @param date  start date
     * @param value the string form of the value
     */
    public abstract void setStringValue(LocalDate date, String value);

    /**
     * return string form of the value
     *
     * @param date base date
     * @return
     */
    public String getValueAsString(LocalDate date) {
        return getValue(date).toString();
    }

    /**
     * get the raw value of item
     *
     * @param date base date
     * @return
     */
    public VT getValue(LocalDate date) {
        VT value = null;
        for (LocalDate key : values.keySet()) {
            if (key.compareTo(date) <= 0) {
                value = values.get(key);
            }
        }
        return value != null ? value : getDefaultValue();
    }

    /**
     * provide default value for the item
     *
     * @return
     */
    public abstract VT getDefaultValue();
}
