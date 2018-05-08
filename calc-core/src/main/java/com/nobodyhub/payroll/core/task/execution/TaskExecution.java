package com.nobodyhub.payroll.core.task.execution;

import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.formula.NormalFormulaContext;
import com.nobodyhub.payroll.core.formula.normal.NormalFormula;
import com.nobodyhub.payroll.core.task.callback.Callback;
import com.nobodyhub.payroll.core.task.execution.context.ExecutionContext;
import lombok.Getter;

/**
 * @author Ryan
 */
@Getter
public class TaskExecution implements Runnable {
    /**
     * Execution Context
     */
    private final ExecutionContext executionContext;
    /**
     * Callback to handle the execution
     */
    private final Callback callback;


    public TaskExecution(ExecutionContext executionContext, Callback callback) {
        this.executionContext = executionContext;
        this.callback = callback;
    }

    @Override
    public void run() {
        callback.onStart();
        NormalFormulaContext normalFormulaContext = executionContext.getTaskContext().getNormalFormulaContext();
        for (NormalFormula formula : normalFormulaContext.getFormulas()) {
            try {
                executionContext.add(formula.evaluate(executionContext));
            } catch (PayrollCoreException e) {
                callback.onError(e, executionContext);
            }
        }
        callback.onCompleted(executionContext);
    }
}
