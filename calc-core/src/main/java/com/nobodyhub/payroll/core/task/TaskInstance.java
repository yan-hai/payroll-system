package com.nobodyhub.payroll.core.task;

import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.formula.FormulaContext;
import com.nobodyhub.payroll.core.formula.common.Formula;
import com.nobodyhub.payroll.core.item.ItemContext;

/**
 * @author Ryan
 */
public class TaskInstance {
    //TODO: take consider of job context
    private TaskContext taskContext;
    private FormulaContext formulaContext;

    public void execute(ItemContext itemContext) throws PayrollCoreException {
        formulaContext.prioritize(itemContext);
        for (Formula formula : formulaContext.getFormulas()) {
            itemContext.add(formula.evaluate(itemContext));
        }
    }
}
