package com.nobodyhub.payroll.core.task;

import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.formula.FormulaContext;
import com.nobodyhub.payroll.core.formula.common.Formula;
import lombok.Getter;

/**
 * @author Ryan
 */
@Getter
public class TaskExecution implements Runnable {
    private final ExecutionContext executionContext;
    private final ExecutionCallback callback;

    public TaskExecution(ExecutionContext executionContext, ExecutionCallback callback) {
        this.executionContext = executionContext;
        this.callback = callback;
    }

    @Override
    public void run() {
        callback.onStart();
        FormulaContext formulaContext = executionContext.getTaskContext().getFormulaContext();
        for (Formula formula : formulaContext.getFormulas()) {
            try {
                executionContext.add(formula.evaluate(executionContext));
            } catch (PayrollCoreException e) {
                callback.onError(e, executionContext);
            }
        }
        callback.onComplete(executionContext);
    }
}
