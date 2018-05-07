package com.nobodyhub.payroll.core.formula.normal.proration;

import com.google.common.collect.Sets;
import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.formula.normal.NormalFormula;
import com.nobodyhub.payroll.core.item.common.Item;
import com.nobodyhub.payroll.core.item.payment.PaymentItem;
import com.nobodyhub.payroll.core.task.ExecutionContext;

import java.math.BigDecimal;
import java.util.Set;

/**
 * @author Ryan
 */
public class ProrationFormula extends NormalFormula {
    private String partItemId;
    private String totalItemId;
    private String paymentItemId;

    @Override
    @SuppressWarnings("unchecked")
    public PaymentItem evaluate(ExecutionContext context) throws PayrollCoreException {
        Item<BigDecimal, ?> part = itemFactory.getItem(partItemId);
        Item<BigDecimal, ?> total = itemFactory.getItem(totalItemId);
        Item<BigDecimal, ?> payment = itemFactory.getItem(paymentItemId);

        BigDecimal rst = part.getValue().multiply(payment.getValue()).divide(total.getValue(), context.getMathContext());
        return createPaymentItem(rst);
    }

    @Override
    public Set<String> getRequiredItems() {
        return Sets.newHashSet(partItemId, totalItemId, paymentItemId);
    }
}
