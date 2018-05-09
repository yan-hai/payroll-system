package com.nobodyhub.payroll.core.task.execution;

import com.nobodyhub.payroll.core.context.ExecutionContext;
import com.nobodyhub.payroll.core.formula.NormalFormulaContainer;
import com.nobodyhub.payroll.core.task.callback.Callback;
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
    protected final ExecutionContext executionContext;
    /**
     * normal formulas
     */
    protected final NormalFormulaContainer normalFormulaContainer;
    /**
     * Callback to handle the execution
     */
    protected final Callback callback;
}
