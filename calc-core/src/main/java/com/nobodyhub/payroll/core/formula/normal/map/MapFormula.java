package com.nobodyhub.payroll.core.formula.normal.map;

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
import java.util.SortedSet;
import java.util.TreeMap;

/**
 * A Map form of formula, it maps the conditions to a given value.
 * - CASE 1 => A
 * - CASE 2 => B
 * - CASE 3 => C
 * <p>
 * CASE 1~3 will be evaluated in order, whenever case evaluated as true,
 * corresponding value will be returned immediately without evaluate the remaining CASEs.
 *
 * @author Ryan
 * @since 2018-05-04.
 */
public class MapFormula extends NormalFormula<FormulaCaseSet> {
    public MapFormula(String id, String targetItemId, ItemBuilderFactory itemBuilderFactory) {
        super(id, targetItemId, itemBuilderFactory);
    }

    @Override
    public PaymentItem evaluate(ExecutionContext context) throws PayrollCoreException {
        TreeMap<LocalDate, BigDecimal> results = Maps.newTreeMap();
        for (Map.Entry<LocalDate, FormulaCaseSet> entry : contents.entrySet()) {
            FormulaCaseSet caseSet = entry.getValue();
            LocalDate date = entry.getKey();
            results.put(date, caseSet.evaluate(context, date));
        }
        SortedSet<LocalDate> dateSet = getDateSplit(context);
        for (LocalDate date : dateSet) {
            BigDecimal result = contents.get(date).evaluate(context, date);
            results.put(date, result);
        }
        return createPaymentItem(results);
    }

    @Override
    public Set<String> getRequiredItems() {
        Set<String> itemIds = Sets.newHashSet();
        for (FormulaCaseSet caseSet : contents.values()) {
            itemIds.addAll(caseSet.getRequiredItems());
        }
        return itemIds;
    }

    public SortedSet<LocalDate> getDateSplit(ExecutionContext context) throws PayrollCoreException {
        SortedSet<LocalDate> dateSet = Sets.newTreeSet();
        for (Map.Entry<LocalDate, FormulaCaseSet> entry : contents.entrySet()) {
            dateSet.add(entry.getKey());
            dateSet.addAll(entry.getValue().getDateSplit(context));
        }
        return dateSet;
    }
}
