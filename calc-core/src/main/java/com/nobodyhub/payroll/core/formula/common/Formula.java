package com.nobodyhub.payroll.core.formula.common;

import com.google.common.collect.Maps;
import com.nobodyhub.payroll.core.common.Identifiable;
import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.item.ItemBuilderFactory;
import com.nobodyhub.payroll.core.item.payment.PaymentItem;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

/**
 * Abstract Formula class
 *
 * @author Ryan
 */
@Data
@EqualsAndHashCode
public abstract class Formula implements Comparable<Formula>, Identifiable {
    /**
     * Unique formula id
     */
    protected final String id;
    /**
     * the id of item whose value will be evaluated from this formula
     */
    protected final String targetItemId;
    /**
     * Get the instance of various types of items
     */
    protected final ItemBuilderFactory itemBuilderFactory;
    /**
     * The priority which decide the order to evaluate the formula
     * <p>
     * A smaller number means A higher priority and evaluated earlier
     */
    protected int priority = 1000;

    /**
     * Get ids of required items in order to evaluate the formula
     *
     * @return
     */
    public abstract Set<String> getRequiredItems();

    /**
     * Formula will be sorted by its priority
     * Note: f1.compareTo(f2) does not comply with f1.equals(f2)
     *
     * @param o
     * @return
     */
    @Override
    public int compareTo(Formula o) {
        return this.priority - o.priority;
    }

    /**
     * create payment item from given values
     *
     * @param values
     * @return
     * @throws PayrollCoreException
     */
    protected PaymentItem createPaymentItem(Map<LocalDate, BigDecimal> values) throws PayrollCoreException {
        PaymentItem item = (PaymentItem) itemBuilderFactory.getItem(targetItemId);
        item.addAll(values);
        return item;
    }

    /**
     * create payment item from given value
     *
     * @param date
     * @param value
     * @return
     * @throws PayrollCoreException
     */
    protected PaymentItem createPaymentItem(LocalDate date, BigDecimal value) throws PayrollCoreException {
        Map<LocalDate, BigDecimal> map = Maps.newHashMap();
        map.put(date, value);
        return createPaymentItem(map);
    }

}
