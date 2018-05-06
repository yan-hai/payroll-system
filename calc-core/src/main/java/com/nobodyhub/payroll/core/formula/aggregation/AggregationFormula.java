package com.nobodyhub.payroll.core.formula.aggregation;

import com.google.common.collect.Sets;
import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.formula.common.Formula;
import com.nobodyhub.payroll.core.item.ItemContext;
import com.nobodyhub.payroll.core.item.payment.PaymentItem;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Ryan
 */
public class AggregationFormula extends Formula {
    protected Set<String> aggregatedItemIds = Sets.newHashSet();

    @Override
    @SuppressWarnings("unchecked")
    public PaymentItem evaluate(ItemContext context) throws PayrollCoreException {
        BigDecimal rst = BigDecimal.ZERO;
        for (String itemId : aggregatedItemIds) {
            PaymentItem item = (PaymentItem) context.get(itemId);
            item.aggregate(context.getMathContext(), rst);
        }
        PaymentItem item = (PaymentItem) getRequiredItems();
        item.setValue(rst);
        return item;
    }

    @Override
    public Set<String> getRequiredItems() {
        return new HashSet<>(aggregatedItemIds);
    }
}
