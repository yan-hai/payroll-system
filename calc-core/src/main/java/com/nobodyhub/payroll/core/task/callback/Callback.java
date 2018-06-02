package com.nobodyhub.payroll.core.task.callback;

import com.nobodyhub.payroll.core.task.execution.normal.NormalExecutionContext;

/**
 * Callbacks for the execution
 *
 * @author Ryan
 */
public interface Callback {
    /**
     * Before execution start
     *
     * @param context
     */
    void onStart(NormalExecutionContext context);

    /**
     * when execution results in error
     *
     * @param e
     * @param context
     */
    void onError(Exception e, NormalExecutionContext context);

    /**
     * when execution complete without error
     *
     * @param context
     */
    void onCompleted(NormalExecutionContext context);
}
