package com.nobodyhub.payroll.core.task;

import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.item.ItemContext;
import lombok.Getter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Need to be Thread-Safe
 *
 * @author Ryan
 */
@Getter
public abstract class Task {
    protected String taskId;
    protected TaskContext taskContext;
    private ExecutionCallback callback;

    private ExecutorService executorService = Executors.newFixedThreadPool(5);

    public void beforeExec() {
        taskContext.getFormulaContext().prioritize();
    }

    public void execute(ItemContext itemContext) throws PayrollCoreException {
        itemContext.setTaskContext(taskContext);
        TaskExecution execution = new TaskExecution(itemContext, callback);
        executorService.execute(execution);
    }

    public void afterExec() {
        //empty implementation
    }
}
