package com.nobodyhub.payroll.core.task;

import com.nobodyhub.payroll.core.common.Period;
import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.formula.NormalFormulaFactory;
import com.nobodyhub.payroll.core.formula.RetroFormulaFactory;
import com.nobodyhub.payroll.core.item.ItemBuilderFactory;
import com.nobodyhub.payroll.core.item.common.Builder;
import com.nobodyhub.payroll.core.proration.ProrationFactory;
import com.nobodyhub.payroll.core.service.proto.PayrollCoreProtocol;
import com.nobodyhub.payroll.core.task.callback.ExecutionCallback;
import com.nobodyhub.payroll.core.task.execution.normal.NormalExecutionContext;
import com.nobodyhub.payroll.core.task.execution.normal.NormalTaskExecution;
import com.nobodyhub.payroll.core.task.execution.retro.HistoryData;
import com.nobodyhub.payroll.core.task.execution.retro.RetroTaskExecution;
import io.grpc.stub.StreamObserver;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;

/**
 * Need to be Thread-Safe
 *
 * @author Ryan
 */
@Data
@RequiredArgsConstructor
public class Task implements Builder<Task> {
    /**
     * Task id
     */
    protected final String id;
    /**
     * Item factory to provide the item instance
     */
    protected final ItemBuilderFactory itemBuilderFactory;
    /**
     * Formula factory to get normal formulas
     */
    protected final NormalFormulaFactory normalFormulaFactory;
    /**
     * Formula factory to get retroactive formulas
     */
    protected final RetroFormulaFactory retroFormulaFactory;
    /**
     * Proration factory to get proration rules
     */
    protected final ProrationFactory prorationFactory;
    /**
     * Callback to handle the execution
     */
    protected ExecutionCallback executionCallback;
    /**
     * Phaser to await all calculation to finish before reply complete the stream
     */
    private final Phaser phaser = new Phaser(1);

    /**
     * Thread pool, shared by all tasks
     * TODO: use ThreadPoolExecutor instead and decide the pool size based on the # of CPUs
     */
    protected static final ExecutorService executorService
            = Executors.newFixedThreadPool(5);

    /**
     * setup before task starts, if necessary
     */
    public void setup(StreamObserver<PayrollCoreProtocol.Response> responseObserver) {
        this.executionCallback.setResponseObserver(responseObserver);
        this.executionCallback.setTask(this);
    }

    /**
     * Execute normal task
     *
     * @param dataId
     * @param periodValue
     * @throws PayrollCoreException
     */
    protected void executeNormal(String dataId, PayrollCoreProtocol.PeriodValue periodValue) throws PayrollCoreException {
        NormalTaskExecution execution = new NormalTaskExecution(
                createExecutionContext(Period.of(periodValue.getStartDate(), periodValue.getEndDate(), periodValue.getBaseDate()),
                        dataId,
                        periodValue.getItemsList()),
                normalFormulaFactory,
                executionCallback);
        executorService.execute(execution);
    }

    /**
     * Execute retro task
     *
     * @param dataId
     * @param periodValue
     * @param historyData
     * @throws PayrollCoreException
     */
    protected void executeRetro(String dataId, PayrollCoreProtocol.PeriodValue periodValue, HistoryData historyData) throws PayrollCoreException {
        RetroTaskExecution execution = new RetroTaskExecution(
                createExecutionContext(Period.of(periodValue.getStartDate(), periodValue.getEndDate(), periodValue.getBaseDate()),
                        dataId,
                        periodValue.getItemsList()),
                historyData,
                normalFormulaFactory,
                retroFormulaFactory,
                executionCallback);
        executorService.execute(execution);
    }

    /**
     * Execute Task
     *
     * @param value
     * @throws PayrollCoreException
     */
    public void execute(PayrollCoreProtocol.Request value) {
        countUp();
        try {
            HistoryData historyData = new HistoryData(value.getDataId(), itemBuilderFactory, value.getPastValuesList());
            if (!historyData.isEmpty()) {
                //retroactive calculation
                executeRetro(value.getDataId(), value.getCurrentValue(), historyData);
            } else {
                //normal calculation
                executeNormal(value.getDataId(), value.getCurrentValue());
            }
        } catch (PayrollCoreException e) {
            //TODO: add logger and handler
            e.printStackTrace();
            countDown();
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
     *
     * @param dataId
     * @param itemValueList
     * @return
     * @throws PayrollCoreException
     */
    protected NormalExecutionContext createExecutionContext(Period period, String dataId, List<PayrollCoreProtocol.ItemValue> itemValueList) throws PayrollCoreException {
        NormalExecutionContext normalExecutionContext = new NormalExecutionContext(dataId, period, itemBuilderFactory, prorationFactory);
        normalExecutionContext.addAll(itemValueList);
        return normalExecutionContext;
    }

    @Override
    public Task build() {
        return new Task(id,
                itemBuilderFactory,
                normalFormulaFactory,
                retroFormulaFactory,
                prorationFactory
        );
    }

    /**
     * wait all thread to finish
     */
    public void await() {
        phaser.arriveAndAwaitAdvance();
    }

    /**
     * Register a execution
     */
    public void countUp() {
        phaser.register();
    }

    /**
     * Deregister a execution
     */
    public void countDown() {
        phaser.arriveAndDeregister();
    }
}
