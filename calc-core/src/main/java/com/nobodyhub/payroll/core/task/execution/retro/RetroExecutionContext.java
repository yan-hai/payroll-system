package com.nobodyhub.payroll.core.task.execution.retro;

import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.item.ItemFactory;
import com.nobodyhub.payroll.core.item.calendar.Period;
import com.nobodyhub.payroll.core.proration.ProrationFactory;
import com.nobodyhub.payroll.core.service.proto.PayrollCoreProtocol;
import com.nobodyhub.payroll.core.task.execution.ExecutionContext;
import lombok.Getter;

import java.util.List;

/**
 * Execution Context for retroactive calculation
 *
 * @author yan_h
 * @since 2018-05-08.
 */
@Getter
public class RetroExecutionContext extends ExecutionContext {
    protected final OriginalData originalData = new OriginalData();

    public RetroExecutionContext(String dataId, ItemFactory itemFactory, Period period, ProrationFactory prorationFactory) {
        super(dataId, itemFactory, period, prorationFactory);
    }

    @Override
    public void addAll(List<PayrollCoreProtocol.ItemValue> itemValues) throws PayrollCoreException {
        for (PayrollCoreProtocol.ItemValue itemValue : itemValues) {
            add(itemValue.getItemId(), itemValue.getValuesMap());
            originalData.add(itemValue);
        }
    }
}
