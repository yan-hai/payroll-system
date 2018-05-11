package com.nobodyhub.payroll.core.example.facade;

import com.nobodyhub.payroll.core.item.ItemBuilderFactory;
import com.nobodyhub.payroll.core.item.payment.PaymentItem;
import com.nobodyhub.payroll.core.item.payment.rounding.RoundingRule;

/**
 * @author yan_h
 * @since 2018-05-11
 */
public class ExampleItemBuilderFactory extends ItemBuilderFactory {
    /**
     * initialize the contents
     */
    @Override
    public void initContents() {
        add(new PaymentItem("Payment_1", true, "Proration_1", RoundingRule.NA));
    }
}
