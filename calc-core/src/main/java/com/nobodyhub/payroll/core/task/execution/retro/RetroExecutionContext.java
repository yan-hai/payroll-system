package com.nobodyhub.payroll.core.task.execution.retro;

import com.google.common.collect.Maps;
import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.item.ItemFactory;
import com.nobodyhub.payroll.core.item.calendar.Period;
import com.nobodyhub.payroll.core.service.proto.PayrollCoreProtocol;
import com.nobodyhub.payroll.core.task.execution.ExecutionContext;
import com.nobodyhub.payroll.core.util.DateFormatUtils;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

/**
 * Execution Context for retroactive calculation
 *
 * @author yan_h
 * @since 2018-05-08.
 */
@Getter
public class RetroExecutionContext extends ExecutionContext {
    protected final Map<String, SortedMap<LocalDate, String>> originalValues = Maps.newHashMap();

    public RetroExecutionContext(String dataId, ItemFactory itemFactory, Period period) {
        super(dataId, itemFactory, period);
    }

    @Override
    public void addAll(List<PayrollCoreProtocol.ItemValue> itemValues) throws PayrollCoreException {
        for (PayrollCoreProtocol.ItemValue itemValue : itemValues) {
            add(itemValue.getItemId(), itemValue.getValuesMap());
            addOrigin(itemValue);
        }
    }

    protected void addOrigin(PayrollCoreProtocol.ItemValue itemValue) {
        SortedMap<LocalDate, String> values = Maps.newTreeMap();
        for (Map.Entry<String, String> entry : itemValue.getValuesMap().entrySet()) {
            values.put(DateFormatUtils.parseDate(entry.getKey()), entry.getValue());
        }
        originalValues.put(itemValue.getItemId(), values);
    }
}
