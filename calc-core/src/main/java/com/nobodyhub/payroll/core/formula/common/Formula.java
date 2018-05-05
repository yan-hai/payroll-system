package com.nobodyhub.payroll.core.formula.common;

import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.item.ItemContext;

import java.math.BigDecimal;
import java.time.Period;
import java.util.Set;

/**
 * Formula applied to PayItems in order to get numeric value
 *
 * @author yan_h
 * @since 2018-05-04.
 */
public abstract class Formula {

    /**
     *  the id of item whose value will be evaluated from this formula
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
     * Calculate the result value of applying the formula
     *
     * @param context context contains all items
     * @return result value of formula evaluated in the <code>context</code>
     * @throws PayrollCoreException
     */
    public abstract BigDecimal evaluate(ItemContext context) throws PayrollCoreException;

    /**
     * Get ids of required items in order to evaluate the formula
     * @return
     * @throws PayrollCoreException
     */
    public abstract Set<String> getRequiredItems() throws PayrollCoreException;
}
