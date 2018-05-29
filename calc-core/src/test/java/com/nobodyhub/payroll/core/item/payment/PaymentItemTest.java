package com.nobodyhub.payroll.core.item.payment;

import com.google.common.collect.Maps;
import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.item.payment.rounding.RoundingRule;
import com.nobodyhub.payroll.core.proration.impl.CalendarProration;
import com.nobodyhub.payroll.core.task.execution.ExecutionContext;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

/**
 * @author Ryan
 */
public class PaymentItemTest {
    @Mock
    private CalendarProration proration;
    @Mock
    private ExecutionContext executionContext;

    private PaymentItem paymentItem;

    @Before
    public void setup() throws PayrollCoreException {
        MockitoAnnotations.initMocks(this);
        paymentItem = new PaymentItem("itemId",
                false,
                proration,
                RoundingRule.P1_HALF_UP
        );

        Mockito.when(proration.getFinalValue(executionContext, Maps.newTreeMap()))
                .thenReturn(new BigDecimal("3.45"));
    }

    @Test
    public void testDefaultValue() {
        assertEquals(BigDecimal.ZERO, paymentItem.defaultValue());
        assertEquals("itemId", paymentItem.build().getId());
    }

    @Test
    public void testGetFinalValue() throws PayrollCoreException {
        assertEquals(new BigDecimal("3.5"), paymentItem.getFinalValue(executionContext));
    }

}