package com.nobodyhub.payroll.core.formula;

import com.nobodyhub.payroll.core.exception.PayrollCoreException;

import java.util.List;

/**
 * @author yan_h
 * @since 2018-05-04.
 */
public class FormulaCase {
    private List<FormulaCondition> conditions;

    public boolean evaluate() throws PayrollCoreException {
        boolean result = true;
        for (FormulaCondition condition : conditions) {
            result = result && condition.evaluate();
        }
        return result;
    }
}