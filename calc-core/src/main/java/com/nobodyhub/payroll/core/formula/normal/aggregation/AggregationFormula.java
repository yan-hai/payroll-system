package com.nobodyhub.payroll.core.formula.normal.aggregation;

import com.google.common.collect.Sets;
import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.formula.normal.NormalFormula;
import com.nobodyhub.payroll.core.item.payment.PaymentItem;
import com.nobodyhub.payroll.core.context.ExecutionContext;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Ryan
 */
public class AggregationFormula extends NormalFormula {
    protected Set<String> aggregatedItemIds = Sets.newHashSet();

    @Override
    @SuppressWarnings("unchecked")
    public PaymentItem evaluate(ExecutionContext context) throws PayrollCoreException {
        BigDecimal rst = BigDecimal.ZERO;
        for (String itemId : aggregatedItemIds) {
            PaymentItem item = (PaymentItem) context.get(itemId);
            item.aggregate(rst);
        }
        return createPaymentItem(rst);
    }

    @Override
    public Set<String> getRequiredItems() {
        return new HashSet<>(aggregatedItemIds);
    }
}
