package com.nobodyhub.payroll.core.task;

import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.formula.NormalFormulaContext;
import com.nobodyhub.payroll.core.formula.RetroFormulaContext;
import com.nobodyhub.payroll.core.formula.normal.NormalFormula;
import com.nobodyhub.payroll.core.formula.retro.RetroFormula;
import com.nobodyhub.payroll.core.service.data.HistoryData;
import com.nobodyhub.payroll.core.task.callback.Callback;

import java.util.List;

/**
 * @author yan_h
 * @since 2018-05-08.
 */
public class RetroTaskExecution implements Runnable {
    private final ExecutionContext executionContext;
    private final TaskContext taskContext;
    private final HistoryData historyData;
    private final Callback callback;


    public RetroTaskExecution(ExecutionContext executionContext, HistoryData historyData, Callback callback) {
        this.executionContext = executionContext;
        this.taskContext = executionContext.getTaskContext();
        this.historyData = historyData;
        this.callback = callback;
    }

    @Override
    public void run() {
        callback.onStart();
        NormalFormulaContext normalFormulaContext =taskContext.getNormalFormulaContext();
        RetroFormulaContext retroFormulaContext =taskContext.getRetroFormulaContext();
        try {
            //re-calc past data
            List<RetroExecutionContext> retroContexts = historyData.toRetroContexts(executionContext.getDataId(), taskContext);
            for (RetroExecutionContext retroContext : retroContexts) {
                for (NormalFormula formula : normalFormulaContext.getFormulas()) {
                    retroContext.add(formula.evaluate(retroContext));
                }
            }
            //handle diff values
            for (RetroFormula formula : retroFormulaContext.getFormulas()) {
                executionContext.add(formula.evaluate(retroContexts));
            }
        } catch (PayrollCoreException e) {
            callback.onError(e, executionContext);
        }
        callback.onCompleted(executionContext);
    }
}
