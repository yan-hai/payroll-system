package com.nobodyhub.payroll.core.task;

import com.nobodyhub.payroll.core.common.Factory;
import com.nobodyhub.payroll.core.task.callback.ExecutionCallback;

/**
 * Factory to provide the execution callbacks
 *
 * @author yan_h
 * @since 2018-05-11
 */
public class ExecutionCallbackBuilderFactory extends Factory<ExecutionCallback> {
    /**
     * initialize the contents
     *
     * @deprecated no need to initialize, just return a new instance every time
     */
    @Deprecated
    @Override
    public void initContents() {
        //do nothing
    }

    /**
     * get Proration from container
     *
     * @param id
     * @return
     * @deprecated use {@link this#get()} to create a instance
     */
    @Deprecated
    @Override
    public ExecutionCallback get(String id) {
        //do nothing
        return null;
    }

    /**
     * add proration to container
     *
     * @param content
     * @deprecated use {@link this#get()} to create a instance
     */
    @Deprecated
    @Override
    public void add(ExecutionCallback content) {
        //do nothing
    }

    /**
     * create the a new instance of ExecutionCallback
     *
     * @return
     */
    public ExecutionCallback get() {
        return new ExecutionCallback().build();
    }
}
