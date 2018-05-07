package com.nobodyhub.payroll.core.task.callback;

import com.nobodyhub.payroll.core.task.ExecutionContext;

/**
 * Callbacks for the execution
 *
 * @author Ryan
 */
public interface Callback {
    /**
     * Before execution start
     */
    void onStart();

    /**
     * when execution results in error
     *
     * @param e
     * @param context
     */
    void onError(Exception e, ExecutionContext context);

    /**
     * when execution complete without error
     *
     * @param context
     */
    void onCompleted(ExecutionContext context);
}
