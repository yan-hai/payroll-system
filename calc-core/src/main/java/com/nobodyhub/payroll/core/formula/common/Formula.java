package com.nobodyhub.payroll.core.formula.common;

import com.google.common.collect.Maps;
import com.nobodyhub.payroll.core.common.Identifiable;
import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.item.ItemBuilderFactory;
import com.nobodyhub.payroll.core.item.payment.PaymentItem;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

/**
 * @author Ryan
 */
@Data
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

    @Override
    public int compareTo(Formula o) {
        return this.priority - o.priority;
    }

    protected PaymentItem createPaymentItem(Map<LocalDate, BigDecimal> values) throws PayrollCoreException {
        PaymentItem item = (PaymentItem) itemBuilderFactory.getItem(targetItemId);
        item.addAll(values);
        return item;
    }

    protected PaymentItem createPaymentItem(LocalDate date, BigDecimal value) throws PayrollCoreException {
        Map<LocalDate, BigDecimal> map = Maps.newHashMap();
        return createPaymentItem(map);
    }

}
