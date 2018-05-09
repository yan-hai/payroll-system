package com.nobodyhub.payroll.core.context;

import com.google.common.collect.Maps;
import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.item.ItemFactory;
import lombok.Getter;

import java.util.Map;

/**
 * The Execution Context for retroactive calculation
 * @author yan_h
 * @since 2018-05-08.
 */
@Getter
public class RetroExecutionContext extends ExecutionContext {
    protected final Map<String, String> originalValues = Maps.newHashMap();

    public RetroExecutionContext(String dataId, ItemFactory itemFactory) {
        super(dataId, itemFactory);
    }

    @Override
    public void addAll(Map<String, String> valueMap) throws PayrollCoreException {
        super.addAll(valueMap);
        originalValues.putAll(valueMap);
    }
}
