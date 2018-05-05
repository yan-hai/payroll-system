package com.nobodyhub.payroll.core.formula;

import java.math.BigDecimal;
import java.time.Period;
import java.util.List;

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
    /**
     * Valid Period
     */
    protected Period validPeriod;

    protected List<FormulaCase> cases;

    /**
     * Calculate the result value of applying the formula
     *
     * @return
     */
    public abstract BigDecimal evaluate();
}
