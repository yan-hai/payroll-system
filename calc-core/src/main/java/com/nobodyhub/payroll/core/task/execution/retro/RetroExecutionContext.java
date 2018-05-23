package com.nobodyhub.payroll.core.task.execution.retro;

import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.item.ItemBuilderFactory;
import com.nobodyhub.payroll.core.proration.ProrationFactory;
import com.nobodyhub.payroll.core.task.execution.ExecutionContext;
import lombok.Getter;

/**
 * Execution Context for retroactive calculation
 *
 * @author yan_h
 * @since 2018-05-08.
 */
@Getter
public class RetroExecutionContext extends ExecutionContext {
    protected final HistoryData.PeriodData periodData;

    public RetroExecutionContext(String dataId,
                                 HistoryData.PeriodData periodData,
                                 ItemBuilderFactory itemBuilderFactory,
                                 ProrationFactory prorationFactory) throws PayrollCoreException {
        super(dataId, periodData.getPeriod(), itemBuilderFactory, prorationFactory);
        this.periodData = periodData;
        addAll(periodData);
    }
}
