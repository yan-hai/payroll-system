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

    public void execute(Map<String, String> valueMap) throws PayrollCoreException {
        ExecutionContext executionContext = createExecutionContext(valueMap);
        TaskExecution execution = new TaskExecution(executionContext, callback);
        executorService.execute(execution);
    }

    public void cleanup() {
        //empty implementation
    }

    private ExecutionContext createExecutionContext(Map<String, String> valueMap) throws PayrollCoreException {
        ExecutionContext executionContext = new ExecutionContext(taskContext);
        executionContext.addAll(valueMap);
        return executionContext;
    }
}
