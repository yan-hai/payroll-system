package com.nobodyhub.payroll.core.formula.retro;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.nobodyhub.payroll.core.common.Period;
import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.item.ItemBuilderFactory;
import com.nobodyhub.payroll.core.item.hr.HrDateItem;
import com.nobodyhub.payroll.core.item.payment.PaymentItem;
import com.nobodyhub.payroll.core.task.execution.retro.HistoryData;
import com.nobodyhub.payroll.core.task.execution.retro.RetroExecutionContext;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

/**
 * @author yan_h
 * @since 29/05/2018
 */
public class RetroFormulaTest {
    @Mock
    private PaymentItem paymentItem1;
    @Mock
    private PaymentItem paymentItem2;
    @Mock
    private PaymentItem paymentItem3;

    @Mock
    private HistoryData.PeriodData periodData1;
    @Mock
    private RetroExecutionContext retroExecutionContext1;

    @Mock
    private HistoryData.PeriodData periodData2;
    @Mock
    private RetroExecutionContext retroExecutionContext2;

    @Mock
    private ItemBuilderFactory itemBuilderFactory;

    private RetroFormula retroFormula;
    private PaymentItem result;

    @Before
    public void setup() throws PayrollCoreException {
        MockitoAnnotations.initMocks(this);
        result = new PaymentItem("targetItemId", false, null, null);

        Mockito.when(itemBuilderFactory.getItem("targetItemId", PaymentItem.class)).thenReturn(result);
        Mockito.when(itemBuilderFactory.getItem("targetItemId")).thenReturn(result);

        retroFormula = new RetroFormula("RetroFormulaId", "targetItemId", itemBuilderFactory);

    }

    @Test
    public void testValidate() throws PayrollCoreException {
        Mockito.when(itemBuilderFactory.getItem("targetItemId"))
                .thenReturn(new PaymentItem("targetItemId", false, null, null));
        Mockito.when(itemBuilderFactory.getItem("item1"))
                .thenReturn(new PaymentItem("item1", true, null, null));
        this.retroFormula.addContent(LocalDate.of(2018, 5, 1),
                Sets.newHashSet("item1"));
        this.retroFormula.validate();

        Mockito.when(itemBuilderFactory.getItem("item2"))
                .thenReturn(new PaymentItem("item2", false, null, null));
        this.retroFormula.addContent(LocalDate.of(2018, 5, 10),
                Sets.newHashSet("item2"));
        try {
            this.retroFormula.validate();
        } catch (PayrollCoreException e) {
            assertEquals(
                    "Items with Ids are either not retro items or not payment items: [item2]",
                    e.getValue(PayrollCoreException.KEY_ERROR_MESSAGE));
        }

        Mockito.when(itemBuilderFactory.getItem("item3"))
                .thenReturn(new HrDateItem("item3"));
        this.retroFormula.addContent(LocalDate.of(2018, 5, 20),
                Sets.newHashSet("item3"));
        try {
            this.retroFormula.validate();
        } catch (PayrollCoreException e) {
            assertEquals(
                    "Items with Ids are either not retro items or not payment items: [item2,item3]",
                    e.getValue(PayrollCoreException.KEY_ERROR_MESSAGE));
        }
    }

    @Test
    public void testEvaluate() throws PayrollCoreException {
        Mockito.when(itemBuilderFactory.getItem("item1")).thenReturn(paymentItem1);
        Mockito.when(itemBuilderFactory.getItem("item2")).thenReturn(paymentItem2);
        Mockito.when(itemBuilderFactory.getItem("item3")).thenReturn(paymentItem3);
        Mockito.when(paymentItem1.isRetro()).thenReturn(true);
        Mockito.when(paymentItem2.isRetro()).thenReturn(true);
        Mockito.when(paymentItem3.isRetro()).thenReturn(true);

        Mockito.when(retroExecutionContext1.getPeriod()).thenReturn(Period.of("20180301", "20180331"));
        Mockito.when(retroExecutionContext1.getPeriodData()).thenReturn(periodData1);
        Mockito.when(retroExecutionContext1.get("item1")).thenReturn(paymentItem1);
        Mockito.when(retroExecutionContext1.get("item2")).thenReturn(paymentItem2);
        Mockito.when(retroExecutionContext1.get("item1", PaymentItem.class)).thenReturn(paymentItem1);
        Mockito.when(retroExecutionContext1.get("item2", PaymentItem.class)).thenReturn(paymentItem2);
        Mockito.when(paymentItem1.getFinalValue(retroExecutionContext1)).thenReturn(new BigDecimal("100"));
        Mockito.when(paymentItem2.getFinalValue(retroExecutionContext1)).thenReturn(new BigDecimal("200"));
        Mockito.when(periodData1.getPayment("item1", itemBuilderFactory)).thenReturn(new BigDecimal("10"));
        Mockito.when(periodData1.getPayment("item2", itemBuilderFactory)).thenReturn(new BigDecimal("20"));

        Mockito.when(retroExecutionContext2.getPeriod()).thenReturn(Period.of("20180401", "20180430"));
        Mockito.when(retroExecutionContext2.getPeriodData()).thenReturn(periodData2);
        Mockito.when(retroExecutionContext2.get("item2")).thenReturn(paymentItem2);
        Mockito.when(retroExecutionContext2.get("item3")).thenReturn(paymentItem3);
        Mockito.when(retroExecutionContext2.get("item2", PaymentItem.class)).thenReturn(paymentItem2);
        Mockito.when(retroExecutionContext2.get("item3", PaymentItem.class)).thenReturn(paymentItem3);
        Mockito.when(paymentItem2.getFinalValue(retroExecutionContext2)).thenReturn(new BigDecimal("220"));
        Mockito.when(paymentItem3.getFinalValue(retroExecutionContext2)).thenReturn(new BigDecimal("330"));
        Mockito.when(periodData2.getPayment("item2", itemBuilderFactory)).thenReturn(new BigDecimal("22"));
        Mockito.when(periodData2.getPayment("item3", itemBuilderFactory)).thenReturn(new BigDecimal("33"));


        List<RetroExecutionContext> retroExecutionContexts = Lists.newArrayList(
                retroExecutionContext1,
                retroExecutionContext2
        );


        retroFormula.addContent(LocalDate.of(2018, 3, 1),
                Sets.newHashSet("item1", "item2"));
        retroFormula.addContent(LocalDate.of(2018, 4, 1),
                Sets.newHashSet("item2", "item3"));

        PaymentItem item = this.retroFormula.evaluate(retroExecutionContexts, Period.of("20150101", "20180531"));
        assertEquals(true, item == result);
        assertEquals(1, item.getValues().size());
        assertEquals(new BigDecimal("765"), item.getValue(LocalDate.of(2018, 5, 1)));
    }
}