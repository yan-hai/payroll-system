package com.nobodyhub.payroll.core.task;

import com.google.common.collect.Maps;
import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import lombok.Getter;

import java.util.Map;

/**
 * @author yan_h
 * @since 2018-05-08.
 */
@Getter
public class RetroExecutionContext extends ExecutionContext {
    protected final Map<String, String> originalValues = Maps.newHashMap();

    public RetroExecutionContext(String dataId, TaskContext taskContext) {
        super(dataId, taskContext);
    }

    @Override
    public void addAll(Map<String, String> valueMap) throws PayrollCoreException {
        super.addAll(valueMap);
        originalValues.putAll(valueMap);
    }
}
