package com.nobodyhub.payroll.core.formula.normal.map;

import com.google.common.collect.Sets;
import com.nobodyhub.payroll.core.common.Period;
import com.nobodyhub.payroll.core.exception.PayrollCoreException;
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

import static junit.framework.TestCase.assertEquals;

/**
 * @author yan_h
 * @since 28/05/2018
 */
public class FormulaCaseSetTest {
    private FormulaCaseSet formulaCaseSet;

    @Mock
    private FormulaCase case1;
    @Mock
    private FormulaCase case2;

    private ExecutionContext executionContext;

    @Before
    public void setup() throws PayrollCoreException {
        MockitoAnnotations.initMocks(this);

        executionContext = new NormalExecutionContext("dataId",
                Period.of("20180501", "20180531"),
                null, null);

        Mockito.when(case1.getRequiredItems()).thenReturn(Sets.newHashSet(
                "item11", "item12"
        ));
        Mockito.when(case2.getRequiredItems()).thenReturn(Sets.newHashSet(
                "item21", "item22"
        ));
        Mockito.when(case1.getValue()).thenReturn(new BigDecimal("100"));
        Mockito.when(case2.getValue()).thenReturn(new BigDecimal("200"));

        Mockito.when(case1.getDateSegment(executionContext)).thenReturn(Sets.newHashSet(
                LocalDate.of(2018, 5, 1),
                LocalDate.of(2018, 5, 10)
        ));
        Mockito.when(case2.getDateSegment(executionContext)).thenReturn(Sets.newHashSet(
                LocalDate.of(2018, 5, 1),
                LocalDate.of(2018, 5, 10),
                LocalDate.of(2018, 5, 20)
        ));

        Mockito.when(case1.matches(executionContext, LocalDate.of(2018, 5, 1)))
                .thenReturn(true);
        Mockito.when(case1.matches(executionContext, LocalDate.of(2018, 5, 10)))
                .thenReturn(false);
        Mockito.when(case1.matches(executionContext, LocalDate.of(2018, 5, 20)))
                .thenReturn(false);
        Mockito.when(case2.matches(executionContext, LocalDate.of(2018, 5, 1)))
                .thenReturn(false);
        Mockito.when(case2.matches(executionContext, LocalDate.of(2018, 5, 10)))
                .thenReturn(true);
        Mockito.when(case2.matches(executionContext, LocalDate.of(2018, 5, 20)))
                .thenReturn(false);

        formulaCaseSet = new FormulaCaseSet(new BigDecimal("999"));
        formulaCaseSet.addCase(case1);
        formulaCaseSet.addCase(case2);

    }

    @Test
    public void testGetRequiredItems() {
        Set<String> ids = this.formulaCaseSet.getRequiredItems();
        assertEquals(4, ids.size());
        assertEquals(true, ids.contains("item11"));
        assertEquals(true, ids.contains("item12"));
        assertEquals(true, ids.contains("item21"));
        assertEquals(true, ids.contains("item22"));
    }

    @Test
    public void testGetDateSegment() throws PayrollCoreException {
        Set<LocalDate> dates = this.formulaCaseSet.getDateSegment(executionContext);
        assertEquals(3, dates.size());
        assertEquals(true, dates.contains(LocalDate.of(2018, 5, 1)));
        assertEquals(true, dates.contains(LocalDate.of(2018, 5, 10)));
        assertEquals(true, dates.contains(LocalDate.of(2018, 5, 20)));
    }

    @Test
    public void testEvaluate() throws PayrollCoreException {
        assertEquals(new BigDecimal("100"),
                this.formulaCaseSet.evaluate(executionContext,
                        LocalDate.of(2018, 5, 1)));
        assertEquals(new BigDecimal("200"),
                this.formulaCaseSet.evaluate(executionContext,
                        LocalDate.of(2018, 5, 10)));
        assertEquals(new BigDecimal("999"),
                this.formulaCaseSet.evaluate(executionContext,
                        LocalDate.of(2018, 5, 20)));
    }
}