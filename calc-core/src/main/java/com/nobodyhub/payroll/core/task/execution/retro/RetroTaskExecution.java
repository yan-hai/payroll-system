package com.nobodyhub.payroll.core.task.execution.retro;

import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.formula.NormalFormulaFactory;
import com.nobodyhub.payroll.core.formula.RetroFormulaFactory;
import com.nobodyhub.payroll.core.formula.normal.NormalFormula;
import com.nobodyhub.payroll.core.formula.retro.RetroFormula;
import com.nobodyhub.payroll.core.task.callback.Callback;
import com.nobodyhub.payroll.core.task.execution.TaskExecution;
import com.nobodyhub.payroll.core.task.execution.normal.NormalExecutionContext;

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
    protected final RetroFormulaFactory retroFormulaFactory;
    /**
     * The history data
     */
    private final HistoryData historyData;


    public RetroTaskExecution(NormalExecutionContext normalExecutionContext,
                              HistoryData historyData,
                              NormalFormulaFactory normalFormulaFactory,
                              RetroFormulaFactory retroFormulaFactory,
                              Callback callback) {
        super(normalExecutionContext, normalFormulaFactory, callback);
        this.retroFormulaFactory = retroFormulaFactory;
        this.historyData = historyData;
    }

    @Override
    public void run() {
        callback.onStart();
        try {
            //re-calc past data
            List<RetroExecutionContext> retroContexts = historyData.toRetroContexts(normalExecutionContext.getItemBuilderFactory(),
                    normalExecutionContext.getProrationFactory());
            for (RetroExecutionContext retroContext : retroContexts) {
                for (NormalFormula formula : normalFormulaFactory.getFormulas()) {
                    retroContext.add(formula.evaluate(retroContext));
                }
            }
            //handle diff values
            for (RetroFormula formula : retroFormulaFactory.getFormulas()) {
                normalExecutionContext.add(formula.evaluate(retroContexts, normalExecutionContext.getPeriod()));
            }
        } catch (PayrollCoreException e) {
            callback.onError(e, normalExecutionContext);
        }
        callback.onCompleted(normalExecutionContext);
    }
}
