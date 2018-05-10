package com.nobodyhub.payroll.core.formula.normal.arithmetic;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.formula.normal.NormalFormula;
import com.nobodyhub.payroll.core.item.payment.PaymentItem;
import com.nobodyhub.payroll.core.task.execution.ExecutionContext;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;

/**
 * A NormalFormula formed by Arithmetic Expressions
 *
 * @author Ryan
 */
public class ArithmeticFormula extends NormalFormula<FormulaExpression> {

    @Override
    public PaymentItem evaluate(ExecutionContext context) throws PayrollCoreException {
        TreeMap<LocalDate, BigDecimal> results = Maps.newTreeMap();
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
        for (FormulaExpression expression : contents.values()) {
            expression.getRequiredItems(itemIds);
        }
        return itemIds;
    }

    public SortedSet<LocalDate> getDateSplit(ExecutionContext context) throws PayrollCoreException {
        SortedSet<LocalDate> dateSet = Sets.newTreeSet();
        for (Map.Entry<LocalDate, FormulaExpression> entry : contents.entrySet()) {
            dateSet.add(entry.getKey());
            FormulaExpression curExpr = entry.getValue();
            while (curExpr != null) {
                dateSet.addAll(curExpr.getDateSplit(context));
                curExpr = curExpr.getAnotherOperand();
            }
        }
        return dateSet;
    }
}