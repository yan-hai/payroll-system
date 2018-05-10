package com.nobodyhub.payroll.core.task.execution.retro;

import com.google.common.collect.Maps;
import com.nobodyhub.payroll.core.service.proto.PayrollCoreProtocol;
import com.nobodyhub.payroll.core.util.DateFormatUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.SortedMap;

/**
 * @author yan_h
 * @since 2018-05-10
 */
public class OriginalData {
    private final Map<String, SortedMap<LocalDate, BigDecimal>> dateSet = Maps.newHashMap();

    public void add(PayrollCoreProtocol.ItemValue itemValue) {
        SortedMap<LocalDate, BigDecimal> values = Maps.newTreeMap();
        for (Map.Entry<String, String> entry : itemValue.getValuesMap().entrySet()) {
            values.put(DateFormatUtils.parseDate(entry.getKey()), new BigDecimal(entry.getValue()));
        }
        dateSet.put(itemValue.getItemId(), values);
    }

    public BigDecimal get(String itemId) {
        SortedMap<LocalDate, BigDecimal> values = dateSet.get(itemId);
        return values.values().stream().reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
