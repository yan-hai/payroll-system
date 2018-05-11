package com.nobodyhub.payroll.core.task;

import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.formula.NormalFormulaFactory;
import com.nobodyhub.payroll.core.formula.RetroFormulaFactory;
import com.nobodyhub.payroll.core.item.ItemFactory;
import com.nobodyhub.payroll.core.item.calendar.Period;
import com.nobodyhub.payroll.core.proration.ProrationFactory;
import com.nobodyhub.payroll.core.service.proto.PayrollCoreProtocol;
import com.nobodyhub.payroll.core.task.callback.Callback;
import com.nobodyhub.payroll.core.task.execution.normal.NormalExecutionContext;
import com.nobodyhub.payroll.core.task.execution.normal.NormalTaskExecution;
import com.nobodyhub.payroll.core.task.execution.retro.HistoryData;
import com.nobodyhub.payroll.core.task.execution.retro.RetroTaskExecution;
import lombok.Data;

import java.util.List;
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
     * Item factory to provide the item instance
     */
    protected final ItemFactory itemFactory;
    /**
     * normal formulas
     */
    protected final NormalFormulaFactory normalFormulaFactory;
    /**
     * retroactive formulas
     */
    protected final RetroFormulaFactory retroFormulaFactory;
    /**
     * Proration rules
     */
    protected final ProrationFactory prorationFactory;
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
        normalFormulaFactory.prioritize();
        retroFormulaFactory.prioritize();
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
                createExecutionContext(Period.of(periodValue.getStartDate(), periodValue.getEndDate()),
                        dataId,
                        periodValue.getItemsList()),
                normalFormulaFactory,
                callback);
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
                createExecutionContext(Period.of(periodValue.getStartDate(), periodValue.getEndDate()),
                        dataId,
                        periodValue.getItemsList()),
                historyData,
                normalFormulaFactory,
                retroFormulaFactory,
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
        HistoryData historyData = new HistoryData(value.getDataId(), itemFactory, value.getPastValuesList());
        if (!historyData.isEmpty()) {
            //retroactive calculation
            executeRetro(value.getDataId(), value.getCurrentValue(), historyData);
        } else {
            //normal calculation
            executeNormal(value.getDataId(), value.getCurrentValue());
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
        NormalExecutionContext normalExecutionContext = new NormalExecutionContext(dataId, itemFactory, period, prorationFactory);
        normalExecutionContext.addAll(itemValueList);
        return normalExecutionContext;
    }
}
