package com.nobodyhub.payroll.core.item.common;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
     * different subclass may parse these values in different value,
     * e.g. use the LocalDate as start date or use as specific date
     */
    protected TreeMap<LocalDate, VT> values = Maps.newTreeMap((o1, o2) -> (o1.compareTo(o2) * (-1)));

    /**
     * set the item's raw value
     *
     * @param date  start date to be effective
     * @param value
     */
    public void addValue(LocalDate date, VT value) {
        this.values.put(date, value);
    }

    /**
     * set the item's raw values
     *
     * @param values new value map
     */
    public void addValues(Map<LocalDate, VT> values) {
        this.values.putAll(values);
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

    /**
     * get the start dates for the {@link this#values}
     *
     * @return
     */
    public Set<LocalDate> getDateSplit() {
        return values.keySet();
    }

    public VT getSingleValue() {
        List<VT> valueList = Lists.newArrayList(values.values());
        return valueList.isEmpty() ? null : valueList.get(0);
    }
}
