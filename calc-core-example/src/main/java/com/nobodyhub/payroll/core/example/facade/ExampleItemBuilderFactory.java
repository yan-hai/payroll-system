package com.nobodyhub.payroll.core.example.facade;

import com.nobodyhub.payroll.core.item.ItemBuilderFactory;
import com.nobodyhub.payroll.core.item.payment.PaymentItem;
import com.nobodyhub.payroll.core.item.payment.rounding.RoundingRule;
import com.nobodyhub.payroll.core.proration.ProrationFactory;

/**
 * @author yan_h
 * @since 2018-05-11
 */
public class ExampleItemBuilderFactory extends ItemBuilderFactory {
    public ExampleItemBuilderFactory(ProrationFactory prorationFactory) {
        super(prorationFactory);
    }

    /**
     * initialize the contents
     */
    @Override
    public void initContents() {
        add(new PaymentItem("Payment_1", true, prorationFactory.get("Proration_1"), RoundingRule.NA));
    }
}
