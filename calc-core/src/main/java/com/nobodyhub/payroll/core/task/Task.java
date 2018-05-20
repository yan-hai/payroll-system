package com.nobodyhub.payroll.core.task;

import com.nobodyhub.payroll.core.common.Identifiable;
import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.formula.NormalFormulaFactory;
import com.nobodyhub.payroll.core.formula.RetroFormulaFactory;
import com.nobodyhub.payroll.core.item.ItemBuilderFactory;
import com.nobodyhub.payroll.core.item.calendar.Period;
import com.nobodyhub.payroll.core.proration.ProrationFactory;
import com.nobodyhub.payroll.core.service.proto.PayrollCoreProtocol;
import com.nobodyhub.payroll.core.task.callback.ExecutionCallback;
import com.nobodyhub.payroll.core.task.execution.normal.NormalExecutionContext;
import com.nobodyhub.payroll.core.task.execution.normal.NormalTaskExecution;
import com.nobodyhub.payroll.core.task.execution.retro.HistoryData;
import com.nobodyhub.payroll.core.task.execution.retro.RetroTaskExecution;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Need to be Thread-Safe
 *
 * @author Ryan
 */
@Data
@RequiredArgsConstructor
public class Task implements Identifiable {
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
     * Thread pool, shared by all tasks
     * TODO: use ThreadPoolExecutor instead and decide the pool size based on the # of CPUs
     */
    protected static final ExecutorService executorService
            = Executors.newFixedThreadPool(5);

    /**
     * setup before task starts
     */
    public void setup() {
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
    public void execute(PayrollCoreProtocol.Request value) throws PayrollCoreException {
        HistoryData historyData = new HistoryData(value.getDataId(), itemBuilderFactory, value.getPastValuesList());
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
        NormalExecutionContext normalExecutionContext = new NormalExecutionContext(dataId, itemBuilderFactory, period, prorationFactory);
        normalExecutionContext.addAll(itemValueList);
        return normalExecutionContext;
    }
}
