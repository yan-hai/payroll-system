package com.nobodyhub.payroll.core.example.facade;

import com.nobodyhub.payroll.core.item.ItemBuilderFactory;
import com.nobodyhub.payroll.core.item.calendar.CalendarItem;
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
        addPaymentItems();
        addCalendarItems();
    }

    private void addPaymentItems() {
        add(new PaymentItem("fix_salary",
                true,
                "calendarday_prorated",
                RoundingRule.NA));
        add(new PaymentItem("unfix_salary",
                true,
                "workday_prorated",
                RoundingRule.NA));
        add(new PaymentItem("OT_allowance",
                true,
                "workday_prorated",
                RoundingRule.NA));
    }

    private void addCalendarItems() {
        add(new CalendarItem("workday_item"));
        add(new CalendarItem("calendarday_item"));
    }

}
