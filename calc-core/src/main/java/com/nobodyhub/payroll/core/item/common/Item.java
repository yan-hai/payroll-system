package com.nobodyhub.payroll.core.item.common;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.util.ValueConverter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import static com.nobodyhub.payroll.core.exception.PayrollCoreExceptionCode.ITEM_VALUE_UNKNOWN;

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
    protected final String id;
    /**
     * class of values
     */
    protected final Class<VT> valueCls;
    /**
     * values for different period, from the latest to the oldest
     * different subclass may parse these values in different ways,
     * for example,
     * <p></p>
     * - for payment items, monthly salary
     * 20180401 -> 3000
     * 20180416 -> 4000
     * means:
     * for 0401~0415, monthly salary is 3000 and for 0416 onwards monthly salary is 4000.
     * which, after prorated by calender day, makes the final actual monthly salary to
     * 3000*15/30 + 4000* 15/30 = 3500
     * <p></p>
     * - for calendar items, working days
     * 20180401 -> 1
     * 20180416 -> 0
     * 20180417 -> 1
     * means:
     * 0401~0415 are working days
     * 0416 is non-working day
     * 0417 onwards are working days
     */
    protected TreeMap<LocalDate, String> values = Maps.newTreeMap((o1, o2) -> (o1.compareTo(o2) * (-1)));

    /**
     * set the item value
     *
     * @param date  start date to be effective
     * @param value value to be added
     * @throws PayrollCoreException if can not be handled by {@link this#convertToString}
     */
    public void add(LocalDate date, VT value) throws PayrollCoreException {
        if (value == null
                || valueCls.isAssignableFrom(value.getClass())
                || value.getClass() == String.class) {
            this.values.put(date, convertToString(value));
            return;
        }
        throw new PayrollCoreException(ITEM_VALUE_UNKNOWN)
                .addValue("value", value);
    }

    /**
     * set the item values
     *
     * @param values value map to be added
     * @throws PayrollCoreException if can not be handled by {@link this#convertToString}
     */
    public void addAll(Map<LocalDate, VT> values) throws PayrollCoreException {
        for (Map.Entry<LocalDate, VT> entry : values.entrySet()) {
            if (entry.getValue() == null
                    || valueCls.isAssignableFrom(entry.getValue().getClass())
                    || entry.getValue().getClass() == String.class) {
                this.values.put(entry.getKey(), convertToString(entry.getValue()));
            } else {
                throw new PayrollCoreException(ITEM_VALUE_UNKNOWN)
                        .addValue("value", values);
            }
        }
    }

    /**
     * get the raw value of item
     *
     * @param date base date
     * @return
     */
    public VT getValue(LocalDate date) throws PayrollCoreException {
        String value = null;
        for (LocalDate key : values.keySet()) {
            if (key.compareTo(date) <= 0) {
                value = values.get(key);
                break;
            }
        }
        return value != null ? convertFromString(value) : defaultValue();
    }

    /**
     * get values
     *
     * @return
     */
    public SortedMap<LocalDate, VT> getValues() throws PayrollCoreException {
        SortedMap<LocalDate, VT> results = Maps.newTreeMap();
        for (Map.Entry<LocalDate, String> entry : this.values.entrySet()) {
            results.put(entry.getKey(), convertFromString(entry.getValue()));
        }
        return results;
    }

    /**
     * Try to convert string {@code value} to the expected class
     *
     * @param value string value to be conveted
     * @return the value of type <T>
     * @throws PayrollCoreException if the value can not be converted
     */
    private VT convertFromString(String value) throws PayrollCoreException {
        return ValueConverter.convertFromString(value, valueCls);
    }

    /**
     * convert a object to String
     *
     * @param object object to be converted
     * @return string presentative of object
     * @throws PayrollCoreException
     */
    private String convertToString(Object object) throws PayrollCoreException {
        return ValueConverter.convertToString(object);
    }

    /**
     * provide default value for the item
     *
     * @return
     */
    public abstract VT defaultValue();

    /**
     * get the start dates for the {@link this#values}
     *
     * @return
     */
    public Set<LocalDate> getDateSegment() {
        return Sets.newHashSet(values.keySet());
    }
}
