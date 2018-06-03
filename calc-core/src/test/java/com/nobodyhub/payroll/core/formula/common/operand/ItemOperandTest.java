package com.nobodyhub.payroll.core.formula.common.operand;

import com.google.common.collect.Maps;
import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.formula.common.Function;
import com.nobodyhub.payroll.core.formula.normal.arithmetic.operand.ItemArithmeticOperand;
import com.nobodyhub.payroll.core.item.payment.PaymentItem;
import com.nobodyhub.payroll.core.task.execution.ExecutionContext;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.SortedMap;

import static junit.framework.TestCase.assertEquals;

/**
 * @author yan_h
 * @since 2018/6/3
 */
public class ItemOperandTest {
    private ItemArithmeticOperand operand;
    @Mock
    private ExecutionContext executionContext;

    @Mock
    private PaymentItem paymentItem;

    @Before
    public void setup() throws PayrollCoreException {
        SortedMap<LocalDate, BigDecimal> values = Maps.newTreeMap();
        values.put(LocalDate.of(2018, 6, 1),
                new BigDecimal("300"));
        values.put(LocalDate.of(2018, 6, 11),
                new BigDecimal("600"));
        values.put(LocalDate.of(2018, 6, 21),
                new BigDecimal("900"));

        MockitoAnnotations.initMocks(this);
        Mockito.when(executionContext.get("item")).thenReturn(paymentItem);
        Mockito.when(paymentItem.getValueCls()).thenReturn(BigDecimal.class);
        Mockito.when(paymentItem.getValues()).thenReturn(values);
        Mockito.when(executionContext.getItemValue("item",
                LocalDate.of(2018, 6, 1),
                BigDecimal.class)).thenReturn(new BigDecimal("300"));
    }

    @Test
    public void testGetValue() throws PayrollCoreException {
        operand = ItemArithmeticOperand.of("item", Function.COUNT);
        assertEquals(new BigDecimal("3"),
                operand.getValue(
                        executionContext,
                        LocalDate.of(2018, 6, 1)));

        operand = ItemArithmeticOperand.of("item");
        assertEquals(new BigDecimal("300"),
                operand.getValue(
                        executionContext,
                        LocalDate.of(2018, 6, 1)));
    }
}