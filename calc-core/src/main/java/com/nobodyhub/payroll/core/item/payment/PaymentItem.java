package com.nobodyhub.payroll.core.item.payment;

import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.item.common.Item;
import com.nobodyhub.payroll.core.item.payment.rounding.RoundingRule;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

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
        super(itemId);
        this.paymentType = paymentType;
        this.isRetro = isRetro;
        this.roundingRule = roundingRule;
    }

    @Override
    public void setStringValue(LocalDate date, String value) {
        this.values.put(date, new BigDecimal(value));
    }

    @Override
    public BigDecimal getDefaultValue() {
        return BigDecimal.ZERO;
    }

    @Override
    public PaymentItem build() {
        return new PaymentItem(itemId, paymentType, isRetro, roundingRule);
    }

    /**
     * add {@link this#values} to the <code>toAggregate</code>
     *
     * @param toAggregate
     */
    public BigDecimal aggregate(BigDecimal toAggregate) throws PayrollCoreException {
        return paymentType.aggregate(values, toAggregate);
    }
}
