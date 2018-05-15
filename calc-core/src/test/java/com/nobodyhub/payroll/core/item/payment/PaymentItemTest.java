package com.nobodyhub.payroll.core.item.payment;

import com.nobodyhub.payroll.core.item.payment.rounding.RoundingRule;
import com.nobodyhub.payroll.core.proration.impl.CalendarProration;
import org.junit.Test;

/**
 * @author Ryan
 */
public class PaymentItemTest {
    private CalendarProration proration = new CalendarProration("prorationId", "calendarId");

    private PaymentItem paymentItem = new PaymentItem("itemId",
            true,
            proration,
            RoundingRule.P1_HALF_UP
    );


    @Test
    public void testBuild() {

    }

}