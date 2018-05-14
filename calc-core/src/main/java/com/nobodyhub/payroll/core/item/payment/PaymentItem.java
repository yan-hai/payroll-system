package com.nobodyhub.payroll.core.item.payment;

import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.item.common.Item;
import com.nobodyhub.payroll.core.item.payment.rounding.RoundingRule;
import com.nobodyhub.payroll.core.proration.abstr.Proration;
import com.nobodyhub.payroll.core.task.execution.ExecutionContext;
import lombok.Getter;

import java.math.BigDecimal;

/**
 * [Payment] Payment item with BigDecimal type
 *
 * @author yan_h
 * @since 2018-05-04.
 */
@Getter
public class PaymentItem extends Item<BigDecimal, PaymentItem> {
    /**
     * whether the item is subject to retroactive calculation
     */
    private final boolean isRetro;
    /**
     * The prorate rule used to get the final value of this item
     */
    private final Proration proration;
    /**
     * The rounding rule to round the final value
     */
    private final RoundingRule roundingRule;

    public PaymentItem(String itemId,
                       boolean isRetro,
                       Proration proration,
                       RoundingRule roundingRule) {
        super(itemId, BigDecimal.class);
        this.isRetro = isRetro;
        this.proration = proration;
        this.roundingRule = roundingRule;
    }

    @Override
    public PaymentItem build() {
        return new PaymentItem(id, isRetro, proration, roundingRule);
    }

    /**
     * get the final value after prorate and rounding
     *
     * @param context
     * @return
     * @throws PayrollCoreException
     */
    public BigDecimal getFinalValue(ExecutionContext context) throws PayrollCoreException {
        BigDecimal proratedVal = proration.getFinalValue(context, getValues());
        return roundingRule.round(proratedVal);
    }

    @Override
    public BigDecimal defaultValue() {
        return BigDecimal.ZERO;
    }
}
