package com.nobodyhub.payroll.core.formula.normal.map;

import com.nobodyhub.payroll.core.common.Period;
import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.formula.common.Comparator;
import com.nobodyhub.payroll.core.formula.normal.map.operand.ItemConditionOperand;
import com.nobodyhub.payroll.core.formula.normal.map.operand.ValueConditionOperand;
import com.nobodyhub.payroll.core.item.hr.HrNumberItem;
import com.nobodyhub.payroll.core.task.execution.ExecutionContext;
import com.nobodyhub.payroll.core.task.execution.normal.NormalExecutionContext;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import static junit.framework.TestCase.assertEquals;


/**
 * @author yan_h
 * @since 27/05/2018
 */
public class FormulaConditionTest {
    private HrNumberItem conditionItem;
    private FormulaCondition<BigDecimal> condition;
    private HrNumberItem operandItem;
    private ItemConditionOperand<BigDecimal> lower;
    private ValueConditionOperand<BigDecimal> higher;
    private ExecutionContext executionContext;


    @Before
    public void setup() throws PayrollCoreException {
        higher = ValueConditionOperand.of(new BigDecimal("250"));
        operandItem = new HrNumberItem("operandItem");
        operandItem.add(LocalDate.of(2018, 5, 1), null);
        operandItem.add(LocalDate.of(2018, 5, 15), new BigDecimal("200"));
        lower = ItemConditionOperand.of(operandItem);
        conditionItem = new HrNumberItem("conditionItem");
        conditionItem.add(LocalDate.of(2018, 5, 1), new BigDecimal("100"));
        conditionItem.add(LocalDate.of(2018, 5, 20), new BigDecimal("300"));
        condition = new FormulaCondition<>(conditionItem,
                Comparator.C_O,
                lower,
                higher);
        executionContext = new NormalExecutionContext("dateId",
                Period.of("20180501", "20180531"),
                null, null);
        executionContext.add(operandItem);
        executionContext.add(conditionItem);
    }

    @Test
    public void testRequiredIds() {
        Set<String> ids = condition.getRequireIds();
        assertEquals(2, ids.size());
        assertEquals(true, ids.contains("operandItem"));
        assertEquals(true, ids.contains("conditionItem"));
    }

    @Test
    public void testGetDateSegment() throws PayrollCoreException {
        Set<LocalDate> segments = condition.getDateSegment(executionContext);
        assertEquals(3, segments.size());
        assertEquals(true, segments.contains(LocalDate.of(2018, 5, 1)));
        assertEquals(true, segments.contains(LocalDate.of(2018, 5, 15)));
        assertEquals(true, segments.contains(LocalDate.of(2018, 5, 20)));
    }

    @Test
    public void testMatches() throws PayrollCoreException {
        assertEquals(true, condition.matches(executionContext, LocalDate.of(2018, 5, 1)));
        assertEquals(true, condition.matches(executionContext, LocalDate.of(2018, 5, 14)));
        assertEquals(false, condition.matches(executionContext, LocalDate.of(2018, 5, 15)));
        assertEquals(false, condition.matches(executionContext, LocalDate.of(2018, 5, 16)));
        assertEquals(false, condition.matches(executionContext, LocalDate.of(2018, 5, 19)));
        assertEquals(false, condition.matches(executionContext, LocalDate.of(2018, 5, 20)));
        assertEquals(false, condition.matches(executionContext, LocalDate.of(2018, 5, 21)));
        assertEquals(false, condition.matches(executionContext, LocalDate.of(2018, 5, 31)));
    }
}