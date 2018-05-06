package com.nobodyhub.payroll.core.task;

/**
 * Callbacks for the execution
 *
 * @author Ryan
 */
public interface ExecutionCallback {
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
    void onComplete(ExecutionContext context);
}
