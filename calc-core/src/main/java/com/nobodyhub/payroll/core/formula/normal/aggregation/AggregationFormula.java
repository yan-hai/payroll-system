package com.nobodyhub.payroll.core.formula.normal.aggregation;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.formula.normal.NormalFormula;
import com.nobodyhub.payroll.core.item.ItemBuilderFactory;
import com.nobodyhub.payroll.core.item.payment.PaymentItem;
import com.nobodyhub.payroll.core.task.execution.ExecutionContext;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author Ryan
 */
public class AggregationFormula extends NormalFormula<Set<String>> {

    public AggregationFormula(String id, String targetItemId, ItemBuilderFactory itemBuilderFactory) {
        super(id, targetItemId, itemBuilderFactory);
    }

    @Override
    public PaymentItem evaluate(ExecutionContext context) throws PayrollCoreException {
        TreeMap<LocalDate, BigDecimal> results = Maps.newTreeMap();
        BigDecimal rst = BigDecimal.ZERO;
        for (Map.Entry<LocalDate, Set<String>> entry : contents.entrySet()) {
            LocalDate date = entry.getKey();
            for (String itemId : entry.getValue()) {
                BigDecimal value = context.getItemValue(itemId, date, BigDecimal.class);
                rst=rst.add(value);
            }
            results.put(entry.getKey(), rst);
        }
        return createPaymentItem(results);
    }

    @Override
    public Set<String> getRequiredItems() {
        Set<String> idSet = Sets.newHashSet();
        for(Set<String> ids: contents.values()) {
            idSet.addAll(ids);
        }
        return idSet;
    }
}
