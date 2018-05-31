package com.nobodyhub.payroll.core.item.payment;

import com.google.common.collect.Maps;
import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.item.payment.rounding.RoundingRule;
import com.nobodyhub.payroll.core.proration.ProrationFactory;
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
    private ProrationFactory prorationFactory;
    @Mock
    private ExecutionContext executionContext;

    private PaymentItem paymentItem;

    @Before
    public void setup() throws PayrollCoreException {
        MockitoAnnotations.initMocks(this);
        paymentItem = new PaymentItem("itemId",
                false,
                null,
                RoundingRule.P1_HALF_UP
        );

        Mockito.when(
                prorationFactory.prorate(
                        null,
                        executionContext,
                        Maps.newTreeMap())
        ).thenReturn(new BigDecimal("3.45"));
        Mockito.when(
                executionContext.getProrationFactory()
        ).thenReturn(prorationFactory);
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