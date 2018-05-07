package com.nobodyhub.payroll.core.task;

import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.task.callback.Callback;
import lombok.Data;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Need to be Thread-Safe
 *
 * @author Ryan
 */
@Data
public abstract class Task {
    protected final String taskId;
    protected final TaskContext taskContext;
    private Callback callback;

    /**
     * TODO: use ThreadPoolExecutor instead and decide the pool size based on the # of CPUs
     */
    private static final ExecutorService executorService
            = Executors.newFixedThreadPool(5);

    public void setup() {
        taskContext.getFormulaContext().prioritize();
    }

    public void execute(String dataId, Map<String, String> valueMap) throws PayrollCoreException {
        ExecutionContext executionContext = createExecutionContext(dataId, valueMap);
        TaskExecution execution = new TaskExecution(executionContext, callback);
        executorService.execute(execution);
    }

    public void cleanup() {
        //empty implementation
    }

    private ExecutionContext createExecutionContext(String dataId, Map<String, String> valueMap) throws PayrollCoreException {
        ExecutionContext executionContext = new ExecutionContext(dataId, taskContext);
        executionContext.addAll(valueMap);
        return executionContext;
    }
}
