package com.nobodyhub.payroll.core.abstr;

import java.math.BigDecimal;

/**
 * Formula applied to PayItems in order to get numeric value
 *
 * @author yan_h
 * @since 2018-05-04.
 */
public abstract class Formula {
    /**
     * Unique formula id
     */
    protected String formulaId;
    /**
     * Name of formula
     */
    protected String formulaName;

    public abstract BigDecimal evaluate();
}
