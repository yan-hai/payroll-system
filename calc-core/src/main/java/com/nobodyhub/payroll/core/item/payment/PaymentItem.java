package com.nobodyhub.payroll.core.item.payment;

import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.item.common.Item;
import com.nobodyhub.payroll.core.item.payment.rounding.RoundingRule;
import com.nobodyhub.payroll.core.proration.abstr.Proration;
import com.nobodyhub.payroll.core.task.execution.ExecutionContext;
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
    private final boolean isRetro;
    private final String prorationId;
    private final RoundingRule roundingRule;

    public PaymentItem(String itemId,
                       boolean isRetro,
                       String prorationId,
                       RoundingRule roundingRule) {
        super(itemId);
        this.isRetro = isRetro;
        this.prorationId = prorationId;
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
        return new PaymentItem(itemId, isRetro, prorationId, roundingRule);
    }

    public BigDecimal getFinalValue(ExecutionContext context) throws PayrollCoreException {
        Proration proration = context.getProrationContainer().get(prorationId);
        return proration.getFinalValue(context, values);
    }
}
