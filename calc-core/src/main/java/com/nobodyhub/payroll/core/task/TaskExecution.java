package com.nobodyhub.payroll.core.task;

import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.formula.FormulaContext;
import com.nobodyhub.payroll.core.formula.common.Formula;
import com.nobodyhub.payroll.core.item.ItemContext;
import lombok.Getter;

/**
 * @author Ryan
 */
@Getter
public class TaskExecution implements Runnable {
    private final ItemContext itemContext;
    private final ExecutionCallback callback;

    public TaskExecution(ItemContext itemContext, ExecutionCallback callback) {
        this.itemContext = itemContext;
        this.callback = callback;
    }

    @Override
    public void run() {
        callback.onStart();
        FormulaContext formulaContext = itemContext.getTaskContext().getFormulaContext();
        for (Formula formula : formulaContext.getFormulas()) {
            try {
                itemContext.add(formula.evaluate(itemContext));
            } catch (PayrollCoreException e) {
                callback.onError(e, itemContext);
            }
        }
        callback.onComplete(itemContext);
    }
}
