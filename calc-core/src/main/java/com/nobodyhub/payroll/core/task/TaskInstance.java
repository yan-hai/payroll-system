package com.nobodyhub.payroll.core.task;

import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.formula.FormulaContext;
import com.nobodyhub.payroll.core.formula.common.Formula;
import com.nobodyhub.payroll.core.item.ItemContext;

/**
 * @author Ryan
 */
public abstract class TaskInstance {
    protected TaskContext taskContext;
    protected FormulaContext formulaContext;

    public void beforeExec() {
        formulaContext.prioritize();
    }

    public void execute(ItemContext itemContext) throws PayrollCoreException {
        itemContext.setTaskContext(taskContext);
        for (Formula formula : formulaContext.getFormulas()) {
            itemContext.add(formula.evaluate(itemContext));
        }
    }

    public void afterExec() {
        //empty implementation
    }
}
