package com.nobodyhub.payroll.core.task.execution;

import com.nobodyhub.payroll.core.context.*;
import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.formula.NormalFormulaContainer;
import com.nobodyhub.payroll.core.formula.RetroFormulaContainer;
import com.nobodyhub.payroll.core.formula.normal.NormalFormula;
import com.nobodyhub.payroll.core.formula.retro.RetroFormula;
import com.nobodyhub.payroll.core.service.common.HistoryData;
import com.nobodyhub.payroll.core.task.callback.Callback;

import java.util.List;

/**
 * Execution for retroactive task
 *
 * @author yan_h
 * @since 2018-05-08.
 */
public class RetroTaskExecution extends TaskExecution {
    /**
     * retroactive formulas
     */
    protected final RetroFormulaContainer retroFormulaContainer;
    /**
     * The history data
     */
    private final HistoryData historyData;


    public RetroTaskExecution(ExecutionContext executionContext,
                              HistoryData historyData,
                              NormalFormulaContainer normalFormulaContainer,
                              RetroFormulaContainer retroFormulaContainer,
                              Callback callback) {
        super(executionContext, normalFormulaContainer, callback);
        this.retroFormulaContainer = retroFormulaContainer;
        this.historyData = historyData;
    }

    @Override
    public void run() {
        callback.onStart();
        try {
            //re-calc past data
            List<RetroExecutionContext> retroContexts = historyData.toRetroContexts(executionContext.getItemFactory());
            for (RetroExecutionContext retroContext : retroContexts) {
                for (NormalFormula formula : normalFormulaContainer.getFormulas()) {
                    retroContext.add(formula.evaluate(retroContext));
                }
            }
            //handle diff values
            for (RetroFormula formula : retroFormulaContainer.getFormulas()) {
                executionContext.add(formula.evaluate(retroContexts));
            }
        } catch (PayrollCoreException e) {
            callback.onError(e, executionContext);
        }
        callback.onCompleted(executionContext);
    }
}
