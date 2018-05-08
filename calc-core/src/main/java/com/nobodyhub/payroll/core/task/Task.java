package com.nobodyhub.payroll.core.task;

import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.service.common.HistoryData;
import com.nobodyhub.payroll.core.service.proto.PayrollCoreProtocol;
import com.nobodyhub.payroll.core.task.callback.Callback;
import com.nobodyhub.payroll.core.task.execution.RetroTaskExecution;
import com.nobodyhub.payroll.core.task.execution.TaskExecution;
import com.nobodyhub.payroll.core.task.execution.context.ExecutionContext;
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
    /**
     * Task Id
     */
    protected final String taskId;
    /**
     * Task Context
     */
    protected final TaskContext taskContext;
    /**
     * Callback to handle the execution
     */
    protected Callback callback;

    /**
     * Thread pool, shared by all tasks
     * TODO: use ThreadPoolExecutor instead and decide the pool size based on the # of CPUs
     */
    protected static final ExecutorService executorService
            = Executors.newFixedThreadPool(5);

    /**
     * setup before task starts
     */
    public void setup() {
        taskContext.getNormalFormulaContext().prioritize();
        taskContext.getRetroFormulaContext().prioritize();
    }

    /**
     * Execute normal task
     *
     * @param dataId
     * @param valueMap
     * @throws PayrollCoreException
     */
    protected void executeNormal(String dataId, Map<String, String> valueMap) throws PayrollCoreException {
        TaskExecution execution = new TaskExecution(
                createExecutionContext(dataId, valueMap),
                callback);
        executorService.execute(execution);
    }

    /**
     * Execute retro task
     *
     * @param dataId
     * @param valueMap
     * @param historyData
     * @throws PayrollCoreException
     */
    protected void executeRetro(String dataId, Map<String, String> valueMap, HistoryData historyData) throws PayrollCoreException {
        RetroTaskExecution execution = new RetroTaskExecution(
                createExecutionContext(dataId, valueMap),
                historyData,
                callback);
        executorService.execute(execution);
    }

    /**
     * Execute Task
     *
     * @param value
     * @throws PayrollCoreException
     */
    public void execute(PayrollCoreProtocol.Request value) throws PayrollCoreException {
        HistoryData historyData = new HistoryData(value.getHistoriesMap());
        if (!historyData.isEmpty()) {
            //retroactive calculation
            executeRetro(value.getDataId(), value.getValuesMap(), historyData);
        } else {
            //normal calculation
            executeNormal(value.getDataId(), value.getValuesMap());
        }
    }

    /**
     * Cleanup after task finishes
     */
    public void cleanup() {
        //empty implementation
    }

    /**
     * create Execution context based on receive message
     * @param dataId
     * @param valueMap
     * @return
     * @throws PayrollCoreException
     */
    protected ExecutionContext createExecutionContext(String dataId, Map<String, String> valueMap) throws PayrollCoreException {
        ExecutionContext executionContext = new ExecutionContext(dataId, taskContext);
        executionContext.addAll(valueMap);
        return executionContext;
    }
}
