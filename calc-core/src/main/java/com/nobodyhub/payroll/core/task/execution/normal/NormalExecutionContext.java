package com.nobodyhub.payroll.core.task.execution.normal;

import com.nobodyhub.payroll.core.item.ItemBuilderFactory;
import com.nobodyhub.payroll.core.item.calendar.Period;
import com.nobodyhub.payroll.core.proration.ProrationFactory;
import com.nobodyhub.payroll.core.task.execution.ExecutionContext;

/**
 * Execution context for normal(non-retroactive) calculation
 *
 * @author Ryan
 */
public class NormalExecutionContext extends ExecutionContext {

    public NormalExecutionContext(String dataId, ItemBuilderFactory itemBuilderFactory, Period period, ProrationFactory prorationFactory) {
        super(dataId, itemBuilderFactory, prorationFactory, period);
    }
}
