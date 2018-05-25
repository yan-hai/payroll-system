package com.nobodyhub.payroll.core.task.execution.normal;

import com.nobodyhub.payroll.core.common.Period;
import com.nobodyhub.payroll.core.item.ItemBuilderFactory;
import com.nobodyhub.payroll.core.proration.ProrationFactory;
import com.nobodyhub.payroll.core.task.execution.ExecutionContext;

/**
 * Execution context for normal(non-retroactive) calculation
 *
 * @author Ryan
 */
public class NormalExecutionContext extends ExecutionContext {

    public NormalExecutionContext(String dataId,
                                  Period period,
                                  ItemBuilderFactory itemBuilderFactory,
                                  ProrationFactory prorationFactory) {
        super(dataId, period, itemBuilderFactory, prorationFactory);
    }
}
