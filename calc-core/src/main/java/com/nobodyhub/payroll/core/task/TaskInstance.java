package com.nobodyhub.payroll.core.task;

import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.formula.common.Formula;
import com.nobodyhub.payroll.core.item.ItemContext;

/**
 * @author Ryan
 */
public abstract class TaskInstance {
    protected TaskContext taskContext;

    public void beforeExec() {
        taskContext.getFormulaContext().prioritize();
    }

    public void execute(ItemContext itemContext) throws PayrollCoreException {
        itemContext.setTaskContext(taskContext);
        for (Formula formula : taskContext.getFormulaContext().getFormulas()) {
            itemContext.add(formula.evaluate(itemContext));
        }
    }

    public void afterExec() {
        //empty implementation
    }
}
