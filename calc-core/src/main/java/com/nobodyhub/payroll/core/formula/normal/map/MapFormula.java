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

    /**
     * Evaluate the result into a payment item of {@link this#targetItemId}
     *
     * @param context context contains all items
     * @return a payment item of {@link this#targetItemId} containing values
     * @throws PayrollCoreException
     */
    @Override
    public PaymentItem evaluate(ExecutionContext context) throws PayrollCoreException {
        validate();
        Map<LocalDate, BigDecimal> results = Maps.newHashMap();
        Set<LocalDate> dateSet = getDateSegment(context);
        for (LocalDate date : dateSet) {
            BigDecimal result = getContent(date).evaluate(context, date);
            results.put(date, result);
        }
        return createPaymentItem(results);
    }

    /**
     * Get involved item ids
     *
     * @return
     */
    @Override
    public Set<String> getRequiredItems() {
        Set<String> itemIds = Sets.newHashSet();
        for (FormulaCaseSet caseSet : contents.values()) {
            itemIds.addAll(caseSet.getRequiredItems());
        }
        return itemIds;
    }

    /**
     * Get date segments
     *
     * @param context
     * @return
     * @throws PayrollCoreException
     */
    public Set<LocalDate> getDateSegment(ExecutionContext context) throws PayrollCoreException {
        Set<LocalDate> dateSet = Sets.newHashSet();
        for (Map.Entry<LocalDate, FormulaCaseSet> entry : contents.entrySet()) {
            dateSet.add(entry.getKey());
            dateSet.addAll(entry.getValue().getDateSegment(context));
        }
        return dateSet;
    }
}
