package com.nobodyhub.payroll.core.task.execution;

import com.nobodyhub.payroll.core.context.ExecutionContext;
import com.nobodyhub.payroll.core.context.NormalFormulaContainer;
import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.formula.normal.NormalFormula;
import com.nobodyhub.payroll.core.task.callback.Callback;
import lombok.Getter;

/**
 * @author Ryan
 */
@Getter
public class TaskExecution extends Execution {

    public TaskExecution(ExecutionContext executionContext,
                         NormalFormulaContainer normalFormulaContainer,
                         Callback callback) {
        super(executionContext, normalFormulaContainer, callback);
    }

    @Override
    public void run() {
        callback.onStart();
        for (NormalFormula formula : normalFormulaContainer.getFormulas()) {
            try {
                executionContext.add(formula.evaluate(executionContext));
            } catch (PayrollCoreException e) {
                callback.onError(e, executionContext);
            }
        }
        callback.onCompleted(executionContext);
    }
}
