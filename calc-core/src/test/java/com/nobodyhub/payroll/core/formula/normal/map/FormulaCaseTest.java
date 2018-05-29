package com.nobodyhub.payroll.core.formula.normal.map;

import com.google.common.collect.Sets;
import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.task.execution.ExecutionContext;
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
 * @since 27/05/2018
 */
public class FormulaCaseTest {
    @Mock
    private FormulaCondition condition1;

    @Mock
    private FormulaCondition condition2;

    @Mock
    private ExecutionContext executionContext;

    private FormulaCase formulaCase;

    @Before
    public void setup() throws PayrollCoreException {
        MockitoAnnotations.initMocks(this);

        Mockito.when(condition1.getDateSegment(executionContext)).thenReturn(Sets.newHashSet(
                LocalDate.of(2018, 5, 1),
                LocalDate.of(2018, 5, 20)
        ));
        Mockito.when(condition1.getRequireIds()).thenReturn(Sets.newHashSet(
                "item11", "item12"
        ));
        Mockito.when(condition1.matches(executionContext, LocalDate.of(2018, 5, 1)))
                .thenReturn(true);
        Mockito.when(condition1.matches(executionContext, LocalDate.of(2018, 5, 10)))
                .thenReturn(true);
        Mockito.when(condition1.matches(executionContext, LocalDate.of(2018, 5, 20)))
                .thenReturn(false);

        Mockito.when(condition2.getDateSegment(executionContext)).thenReturn(Sets.newHashSet(
                LocalDate.of(2018, 5, 1),
                LocalDate.of(2018, 5, 10)
        ));
        Mockito.when(condition2.getRequireIds()).thenReturn(Sets.newHashSet(
                "item21", "item22"
        ));
        Mockito.when(condition2.matches(executionContext, LocalDate.of(2018, 5, 1)))
                .thenReturn(false);
        Mockito.when(condition2.matches(executionContext, LocalDate.of(2018, 5, 10)))
                .thenReturn(true);
        Mockito.when(condition2.matches(executionContext, LocalDate.of(2018, 5, 20)))
                .thenReturn(true);

        formulaCase = new FormulaCase(0, new BigDecimal("100"));
        formulaCase.addCondition(condition1);
        formulaCase.addCondition(condition2);
    }


    @Test
    public void testGetDateSegment() throws PayrollCoreException {
        Set<LocalDate> dates = formulaCase.getDateSegment(executionContext);
        assertEquals(3, dates.size());
        assertEquals(true, dates.contains(LocalDate.of(2018, 5, 1)));
        assertEquals(true, dates.contains(LocalDate.of(2018, 5, 10)));
        assertEquals(true, dates.contains(LocalDate.of(2018, 5, 20)));
    }

    @Test
    public void testGetRequiredItems() {
        Set<String> ids = formulaCase.getRequiredItems();
        assertEquals(4, ids.size());
        assertEquals(true, ids.contains("item11"));
        assertEquals(true, ids.contains("item12"));
        assertEquals(true, ids.contains("item21"));
        assertEquals(true, ids.contains("item22"));
    }

    @Test
    public void testMatches() throws PayrollCoreException {
        assertEquals(false,
                formulaCase.matches(executionContext, LocalDate.of(2018, 5, 1)));
        assertEquals(true,
                formulaCase.matches(executionContext, LocalDate.of(2018, 5, 10)));
        assertEquals(false,
                formulaCase.matches(executionContext, LocalDate.of(2018, 5, 20)));
    }

    @Test
    public void testCompareTo() {
        assertEquals(true, this.formulaCase.compareTo(new FormulaCase(-1, new BigDecimal("100"))) > 0);
        assertEquals(true, this.formulaCase.compareTo(new FormulaCase(0, new BigDecimal("100"))) == 0);
        assertEquals(true, this.formulaCase.compareTo(new FormulaCase(1, new BigDecimal("100"))) < 0);
    }
}