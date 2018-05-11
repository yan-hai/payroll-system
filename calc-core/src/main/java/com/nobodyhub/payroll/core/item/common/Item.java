package com.nobodyhub.payroll.core.item.common;

import com.google.common.collect.Maps;
import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.util.DateFormatUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

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
    protected final String itemId;
    /**
     * class of values
     */
    protected final Class<VT> valueCls;
    /**
     * values for different period, from the latest to the oldest
     * different subclass may parse these values in different value,
     * e.g. use the LocalDate as start date or use as specific date
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
        if (valueCls.isAssignableFrom(value.getClass())
                || value.getClass() == String.class) {
            this.values.put(date, convertToString(value));
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
            if (valueCls.isAssignableFrom(entry.getValue().getClass())
                    || entry.getValue().getClass() == String.class) {
                this.values.put(entry.getKey(), convertToString(entry.getValue()));
            }
        }
        throw new PayrollCoreException(ITEM_VALUE_UNKNOWN)
                .addValue("value", values);
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
        Objects.requireNonNull(value);
        Object rst;
        if (valueCls == String.class) {
            rst = value;
        } else if (valueCls == BigDecimal.class) {
            rst = new BigDecimal(value);
        } else if (valueCls == Boolean.class) {
            rst = Boolean.valueOf(value);
        } else if (valueCls == LocalDate.class) {
            rst = DateFormatUtils.parseDate(value);
        } else if (valueCls == LocalTime.class) {
            rst = DateFormatUtils.parseTime(value);
        } else if (valueCls == LocalDateTime.class) {
            rst = DateFormatUtils.parseDateTime(value);
        } else {
            throw new PayrollCoreException(ITEM_VALUE_UNKNOWN)
                    .addValue("value", value);
        }
        return valueCls.cast(rst);
    }

    /**
     * convert a object to String
     *
     * @param object object to be converted
     * @return string presentative of object
     * @throws PayrollCoreException
     */
    private String convertToString(Object object) throws PayrollCoreException {
        if (object instanceof String) {
            return (String) object;
        } else if (object instanceof BigDecimal) {
            return ((BigDecimal) object).toPlainString();
        } else if (object instanceof Boolean) {
            return ((Boolean) object).toString();
        } else if (object instanceof LocalDate) {
            return DateFormatUtils.convertDate((LocalDate) object);
        } else if (object instanceof LocalTime) {
            return DateFormatUtils.convertDate((LocalTime) object);
        } else if (object instanceof LocalDateTime) {
            return DateFormatUtils.convertDate((LocalDateTime) object);
        } else {
            throw new PayrollCoreException(ITEM_VALUE_UNKNOWN)
                    .addValue("object", object);
        }
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
        return values.keySet();
    }
}
