package com.nobodyhub.payroll.core.formula.common;

import com.google.common.collect.Maps;
import com.nobodyhub.payroll.core.common.Identifiable;
import com.nobodyhub.payroll.core.common.Period;
import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.item.ItemBuilderFactory;
import com.nobodyhub.payroll.core.item.common.Item;
import com.nobodyhub.payroll.core.item.payment.PaymentItem;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

import static com.nobodyhub.payroll.core.exception.PayrollCoreExceptionCode.RETRO_FORMULA_INVALID;

/**
 * Abstract Formula class
 *
 * @param <CT> type of formula content
 * @author Ryan
 */
@Data
@EqualsAndHashCode
public abstract class Formula<CT> implements Comparable<Formula>, Identifiable {
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
     * <b>Note:</b>
     * if formulas have the same priority, the order is uncertain, although it
     * would affect the execution result
     */
    protected int priority = 1000;

    /**
     * Contents of formula
     * the keys are sorted from the latest to the oldest
     */
    protected final SortedMap<LocalDate, CT> contents = Maps.newTreeMap((o1, o2) -> (o1.compareTo(o2) * (-1)));

    /**
     * Get ids of required items in order to evaluate the formula
     *
     * @return
     */
    public abstract Set<String> getRequiredItems();

    /**
     * validate whether the contents is valid for formula
     * - target item should be a payment item
     *
     * @throws PayrollCoreException
     */
    protected void validate() throws PayrollCoreException {
        Item item = itemBuilderFactory.getItem(targetItemId);
        if (item instanceof PaymentItem) {
            // pass validate
            return;
        }
        throw new PayrollCoreException(RETRO_FORMULA_INVALID)
                .addMessage(String.format("Items with Ids are not payment items: [%s]",
                        targetItemId));
    }


    /**
     * add content
     *
     * @param date
     * @param value
     */
    public void addContent(LocalDate date, CT value) {
        contents.put(date, value);
    }

    /**
     * get the valid content for given date
     *
     * @param basedate
     * @return
     */
    public CT getContent(LocalDate basedate) {
        for (LocalDate date : contents.keySet()) {
            if (date.compareTo(basedate) <= 0) {
                return contents.get(date);
            }
        }
        return null;
    }

    /**
     * get the valid content for given periods
     *
     * @param period
     * @return
     */
    public CT getContent(Period period) {
        return getContent(period.getStart());
    }

    /**
     * create payment item from given values
     *
     * @param values
     * @return
     * @throws PayrollCoreException
     */
    protected PaymentItem createPaymentItem(Map<LocalDate, BigDecimal> values) throws PayrollCoreException {
        PaymentItem item = itemBuilderFactory.getItem(targetItemId, PaymentItem.class);
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

}
