package com.nobodyhub.payroll.core.task.execution.normal;

import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.formula.NormalFormulaContainer;
import com.nobodyhub.payroll.core.formula.normal.NormalFormula;
import com.nobodyhub.payroll.core.task.callback.Callback;
import com.nobodyhub.payroll.core.task.execution.TaskExecution;
import lombok.Getter;

/**
 * @author Ryan
 */
@Getter
public class NormalTaskExecution extends TaskExecution {

    public NormalTaskExecution(NormalExecutionContext normalExecutionContext,
                               NormalFormulaContainer normalFormulaContainer,
                               Callback callback) {
        super(normalExecutionContext, normalFormulaContainer, callback);
    }

    @Override
    public void run() {
        callback.onStart();
        for (NormalFormula formula : normalFormulaContainer.getFormulas()) {
            try {
                normalExecutionContext.add(formula.evaluate(normalExecutionContext));
            } catch (PayrollCoreException e) {
                callback.onError(e, normalExecutionContext);
            }
        }
        callback.onCompleted(normalExecutionContext);
    }
}
