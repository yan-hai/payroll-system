package com.nobodyhub.payroll.core.task.execution;

import com.nobodyhub.payroll.core.formula.NormalFormulaFactory;
import com.nobodyhub.payroll.core.task.callback.Callback;
import com.nobodyhub.payroll.core.task.execution.normal.NormalExecutionContext;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * @author yan_h
 * @since 2018-05-09.
 */
@Data
@RequiredArgsConstructor
public abstract class TaskExecution implements Runnable {
    /**
     * Execution Context
     */
    protected final NormalExecutionContext normalExecutionContext;
    /**
     * normal formulas
     */
    protected final NormalFormulaFactory normalFormulaFactory;
    /**
     * Callback to handle the execution
     */
    protected final Callback callback;
}
