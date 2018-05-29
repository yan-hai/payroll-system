package com.nobodyhub.payroll.core.formula.normal.map;

import com.google.common.collect.Sets;
import com.nobodyhub.payroll.core.common.Period;
import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.item.ItemBuilderFactory;
import com.nobodyhub.payroll.core.item.payment.PaymentItem;
import com.nobodyhub.payroll.core.task.execution.ExecutionContext;
import com.nobodyhub.payroll.core.task.execution.normal.NormalExecutionContext;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * @author yan_h
 * @since 29/05/2018
 */
public class MapFormulaTest {
    private MapFormula mapFormula;

    @Mock
    private FormulaCaseSet content1;
    @Mock
    private FormulaCaseSet content2;

    @Mock
    private ItemBuilderFactory itemBuilderFactory;

    private PaymentItem paymentItem;

    private ExecutionContext executionContext;

    @Before
    public void setup() throws PayrollCoreException {
        MockitoAnnotations.initMocks(this);
        paymentItem = new PaymentItem("paymentId", false, null, null);
        executionContext = new NormalExecutionContext("dataId",
                Period.of("20180501", "20180531"), null, null);
        Mockito.when(itemBuilderFactory.getItem("paymentId", PaymentItem.class))
                .thenReturn(paymentItem);


        Mockito.when(content1.getRequiredItems()).thenReturn(Sets.newHashSet(
                "item11", "item12"
        ));
        Mockito.when(content2.getRequiredItems()).thenReturn(Sets.newHashSet(
                "item21"
        ));

        Mockito.when(content1.getDateSegment(executionContext)).thenReturn(Sets.newHashSet(
                LocalDate.of(2018, 5, 1),
                LocalDate.of(2018, 5, 10)
        ));
        Mockito.when(content2.getDateSegment(executionContext)).thenReturn(Sets.newHashSet(
                LocalDate.of(2018, 5, 1),
                LocalDate.of(2018, 5, 20)
        ));

        Mockito.when(content1.evaluate(executionContext, LocalDate.of(2018, 5, 1)))
                .thenReturn(new BigDecimal("100"));
        Mockito.when(content1.evaluate(executionContext, LocalDate.of(2018, 5, 10)))
                .thenReturn(new BigDecimal("110"));
        Mockito.when(content1.evaluate(executionContext, LocalDate.of(2018, 5, 15)))
                .thenReturn(new BigDecimal("110"));
        Mockito.when(content1.evaluate(executionContext, LocalDate.of(2018, 5, 15)))
                .thenReturn(new BigDecimal("110"));
        Mockito.when(content2.evaluate(executionContext, LocalDate.of(2018, 5, 1)))
                .thenReturn(new BigDecimal("200"));
        Mockito.when(content2.evaluate(executionContext, LocalDate.of(2018, 5, 10)))
                .thenReturn(new BigDecimal("200"));
        Mockito.when(content2.evaluate(executionContext, LocalDate.of(2018, 5, 15)))
                .thenReturn(new BigDecimal("200"));
        Mockito.when(content2.evaluate(executionContext, LocalDate.of(2018, 5, 20)))
                .thenReturn(new BigDecimal("220"));

        Mockito.when(content2.getDateSegment(executionContext)).thenReturn(Sets.newHashSet(
                LocalDate.of(2018, 5, 1),
                LocalDate.of(2018, 5, 20)
        ));


        Mockito.when(itemBuilderFactory.getItem("targetItemId", PaymentItem.class))
                .thenReturn(paymentItem);

        mapFormula = new MapFormula("mapFormulaId", "targetItemId", itemBuilderFactory);
        mapFormula.addContent(LocalDate.of(2018, 5, 1), content1);
        mapFormula.addContent(LocalDate.of(2018, 5, 15), content2);
    }

    @Test
    public void getGetRequiredItems() {
        Set<String> ids = mapFormula.getRequiredItems();
        assertEquals(3, ids.size());
        assertEquals(true, ids.contains("item11"));
        assertEquals(true, ids.contains("item12"));
        assertEquals(true, ids.contains("item21"));
    }

    @Test
    public void testGetDateSegment() throws PayrollCoreException {
        Set<LocalDate> dates = mapFormula.getDateSegment(executionContext);
        assertEquals(4, dates.size());
        assertEquals(true, dates.contains(LocalDate.of(2018, 5, 1)));
        assertEquals(true, dates.contains(LocalDate.of(2018, 5, 10)));
        assertEquals(true, dates.contains(LocalDate.of(2018, 5, 15)));
        assertEquals(true, dates.contains(LocalDate.of(2018, 5, 20)));
    }

    @Test
    public void testEvaluate() throws PayrollCoreException {
        PaymentItem item = mapFormula.evaluate(executionContext);
        assertEquals(true, item == paymentItem);
        assertEquals(new BigDecimal("100"),
                item.getValue(LocalDate.of(2018, 5, 1)));
        assertEquals(new BigDecimal("110"),
                item.getValue(LocalDate.of(2018, 5, 10)));
        assertEquals(new BigDecimal("200"),
                item.getValue(LocalDate.of(2018, 5, 15)));
        assertEquals(new BigDecimal("220"),
                item.getValue(LocalDate.of(2018, 5, 20)));
    }
}