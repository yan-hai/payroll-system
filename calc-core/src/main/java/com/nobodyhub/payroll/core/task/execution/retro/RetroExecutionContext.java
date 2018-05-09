package com.nobodyhub.payroll.core.task.execution.retro;

import com.google.common.collect.Maps;
import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.item.ItemFactory;
import com.nobodyhub.payroll.core.item.calendar.Period;
import com.nobodyhub.payroll.core.task.execution.ExecutionContext;
import lombok.Getter;

import java.util.Map;

/**
 * Execution Context for retroactive calculation
 *
 * @author yan_h
 * @since 2018-05-08.
 */
@Getter
public class RetroExecutionContext extends ExecutionContext {
    protected final Map<String, String> originalValues = Maps.newHashMap();

    public RetroExecutionContext(String dataId, ItemFactory itemFactory, Period period) {
        super(dataId, itemFactory, period);
    }

    @Override
    public void addAll(Map<String, String> valueMap) throws PayrollCoreException {
        super.addAll(valueMap);
        originalValues.putAll(valueMap);
    }
}
