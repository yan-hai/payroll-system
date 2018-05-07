package com.nobodyhub.payroll.core.formula.normal;

import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.item.ItemFactory;
import com.nobodyhub.payroll.core.item.payment.PaymentItem;
import com.nobodyhub.payroll.core.task.ExecutionContext;
import lombok.Data;

import java.time.Period;
import java.util.Set;

/**
 * Formula applied to PayItems in order to get numeric value
 *
 * @author yan_h
 * @since 2018-05-04.
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
     * Valid Period
     */
    protected Period validPeriod;
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
     * Calculate the result value of applying the formula
     *
     * @param context context contains all items
     * @return result value of formula evaluated in the <code>context</code>
     * @throws PayrollCoreException
     */
    public abstract PaymentItem evaluate(ExecutionContext context) throws PayrollCoreException;

    /**
     * create a new instance of PaymentItem to store the evaluate result
     *
     * @return
     * @throws PayrollCoreException
     */
    protected PaymentItem createPaymentItem() throws PayrollCoreException {
        return (PaymentItem) itemFactory.getItem(targetItemId);
    }

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
}
