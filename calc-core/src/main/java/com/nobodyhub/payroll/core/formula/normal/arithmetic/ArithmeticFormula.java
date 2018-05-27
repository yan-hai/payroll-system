package com.nobodyhub.payroll.core.formula.normal.arithmetic;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.nobodyhub.payroll.core.common.Period;
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
 * Formula to perform arithmetic calculation
 *
 * @author Ryan
 */
public class ArithmeticFormula extends NormalFormula<FormulaExpression> {

    public ArithmeticFormula(String id, String targetItemId, ItemBuilderFactory itemBuilderFactory) {
        super(id, targetItemId, itemBuilderFactory);
    }

    @Override
    public PaymentItem evaluate(ExecutionContext context) throws PayrollCoreException {
        TreeMap<LocalDate, BigDecimal> results = Maps.newTreeMap();
        SortedSet<LocalDate> dateSet = getDateSegment(context);
        for (LocalDate date : dateSet) {
            BigDecimal result = getContent(date).evaluate(context, date);
            results.put(date, result);
        }
        return createPaymentItem(results);
    }

    @Override
    public Set<String> getRequiredItems() {
        Set<String> itemIds = Sets.newHashSet();
        for (FormulaExpression expression : contents.values()) {
            itemIds.addAll(expression.getRequiredItems());
        }
        return itemIds;
    }

    /**
     * get the segments of this formula and involved items
     *
     * @param context
     * @return
     * @throws PayrollCoreException
     */
    protected SortedSet<LocalDate> getDateSegment(ExecutionContext context) throws PayrollCoreException {
        SortedSet<LocalDate> segments = Sets.newTreeSet();
        for (Map.Entry<LocalDate, FormulaExpression> entry : contents.entrySet()) {
            // add formula segment
            segments.add(entry.getKey());
            // add segments of involved items
            segments.addAll(entry.getValue().getDateSegment(context));
        }
        Period period = context.getPeriod();
        // ensure the segments starts from period.start
        segments.add(period.getStart());
        // remove dates before the period
        segments.removeIf(period::isAfter);
        // removve dates after the period
        segments.removeIf(period::isBefore);
        return segments;
    }
}