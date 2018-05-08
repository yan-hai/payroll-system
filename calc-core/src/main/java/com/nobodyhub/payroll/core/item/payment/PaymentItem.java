package com.nobodyhub.payroll.core.item.payment;

import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.item.common.Item;
import com.nobodyhub.payroll.core.item.payment.rounding.RoundingRule;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * [Payment] Payitem with BigDecimal type
 *
 * @author yan_h
 * @since 2018-05-04.
 */
@Getter
public class PaymentItem extends Item<BigDecimal, PaymentItem> {
    private final PaymentType paymentType;
    private final boolean isRetro;
    private final RoundingRule roundingRule;

    public PaymentItem(String itemId,
                       PaymentType paymentType,
                       boolean isRetro,
                       RoundingRule roundingRule) {
        super(itemId, BigDecimal.class);
        this.paymentType = paymentType;
        this.isRetro = isRetro;
        this.roundingRule = roundingRule;
    }

    @Override
    public void setStringValue(String value) {
        this.value = new BigDecimal(value);
    }

    @Override
    public BigDecimal getDefaultValue() {
        return BigDecimal.ZERO;
    }

    @Override
    public PaymentItem build() {
        return new PaymentItem(itemId, paymentType, isRetro, roundingRule);
    }

    @Override
    public BigDecimal getValue() {
        return roundingRule.round(super.getValue());
    }

    /**
     * add {@link this#value} to the <code>toAggregate</code>
     *
     * @param toAggregate
     */
    public BigDecimal aggregate(BigDecimal toAggregate) throws PayrollCoreException {
        return paymentType.aggregate(value, toAggregate);
    }
}
