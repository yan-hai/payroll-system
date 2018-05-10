package com.nobodyhub.payroll.core.formula.common;

import com.google.common.collect.Maps;
import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.item.ItemFactory;
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
public abstract class Formula implements Comparable<Formula> {
    /**
     * the id of item whose value will be evaluated from this formula
     */
    protected String targetItemId;
    /**
     * Unique formula id
     */
    protected String formulaId;
    /**
     * Name of formula
     */
    protected String formulaName;
    /**
     * The priority which decide the order to evaluate the formula
     * <p>
     * A smaller number means A higher priority and evaluated earlier
     */
    protected int priority = 1000;

    /**
     * Get the instance of various types of items
     */
    protected ItemFactory itemFactory;

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
        PaymentItem item = (PaymentItem) itemFactory.getItem(targetItemId);
        item.addAll(values);
        return item;
    }

    protected PaymentItem createPaymentItem(LocalDate date, BigDecimal value) throws PayrollCoreException {
        Map<LocalDate, BigDecimal> map = Maps.newHashMap();
        return createPaymentItem(map);
    }

}
