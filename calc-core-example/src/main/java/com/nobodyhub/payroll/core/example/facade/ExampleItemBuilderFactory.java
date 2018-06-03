package com.nobodyhub.payroll.core.example.facade;

import com.nobodyhub.payroll.core.item.ItemBuilderFactory;
import com.nobodyhub.payroll.core.item.calendar.CalendarItem;
import com.nobodyhub.payroll.core.item.hr.HrOptionItem;
import com.nobodyhub.payroll.core.item.payment.PaymentItem;
import com.nobodyhub.payroll.core.item.payment.rounding.RoundingRule;
import com.nobodyhub.payroll.core.proration.ProrationFactory;

import static com.nobodyhub.payroll.core.example.facade.ExampleConst.*;
import static com.nobodyhub.payroll.core.item.payment.rounding.RoundingRule.P1_HALF_UP;

/**
 * Example Item Builder Factory,
 * Item definition would be read from DB.
 *
 * @author yan_h
 * @since 2018-05-11
 */
public class ExampleItemBuilderFactory extends ItemBuilderFactory {
    public ExampleItemBuilderFactory(ProrationFactory prorationFactory) {
        super(prorationFactory);
        initContents();
    }

    /**
     * initialize the contents
     */
    @Override
    public void initContents() {
        addPaymentItems();
        addCalendarItems();
        addHrItems();
    }

    private void addPaymentItems() {

        add(new PaymentItem(PAY_BASIC_SALARY,
                true,
                PRO_WORKDAY,
                RoundingRule.P3_HALF_DOWN));
        add(new PaymentItem(PAY_UNPAID_LEAVE,
                true,
                null,
                RoundingRule.NA));
        add(new PaymentItem(PAY_DAILY_SALARY,
                true,
                null,
                RoundingRule.NA));
        add(new PaymentItem(PAY_OVERTIME_ALLOWANCE,
                true,
                null,
                RoundingRule.NA));
        add(new PaymentItem(PAY_TOTAL_SALARY,
                true,
                null,
                P1_HALF_UP));
    }

    private void addCalendarItems() {
        add(new CalendarItem(CAL_WORK_DAY));
        add(new CalendarItem(CAL_UNPAID_LEAVE));
        add(new CalendarItem(CAL_OVERTIME));
    }

    private void addHrItems() {
        add(new HrOptionItem(HR_POSITION));
    }
}
