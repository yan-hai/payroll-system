package com.nobodyhub.payroll.core.formula.normal.arithmetic;

import com.nobodyhub.payroll.core.common.Period;
import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.formula.common.Operator;
import com.nobodyhub.payroll.core.formula.normal.arithmetic.operand.ItemArithmeticOperand;
import com.nobodyhub.payroll.core.formula.normal.arithmetic.operand.ValueArithmeticOperand;
import com.nobodyhub.payroll.core.formula.normal.arithmetic.operand.abstr.ArithmeticOperand;
import com.nobodyhub.payroll.core.item.ItemBuilderFactory;
import com.nobodyhub.payroll.core.item.hr.HrNumberItem;
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
import java.util.SortedSet;

import static org.junit.Assert.assertEquals;

/**
 * @author yan_h
 * @since 27/05/2018
 */
public class ArithmeticFormulaTest {

    private ArithmeticFormula formula;

    @Mock
    private ItemBuilderFactory itemBuilderFactory;

    private FormulaExpression expression1;
    private ArithmeticOperand operand1;
    private FormulaExpression expression11;
    private ArithmeticOperand operand11;
    private FormulaExpression expression12;
    private ArithmeticOperand operand12;
    private FormulaExpression expression2;
    private ArithmeticOperand operand2;
    private FormulaExpression expression21;
    private ArithmeticOperand operand21;
    private ExecutionContext executionContext;

    private PaymentItem result;

    @Before
    public void setup() throws PayrollCoreException {
        MockitoAnnotations.initMocks(this);
        result = new PaymentItem("targetItemId", false, null, null);
        Mockito.when(itemBuilderFactory.getItem("targetItemId", PaymentItem.class)).thenReturn(result);
        Mockito.when(itemBuilderFactory.getItem("targetItemId")).thenReturn(result);

        operand1 = ItemArithmeticOperand.of("item1");
        operand11 = ItemArithmeticOperand.of("item11");
        operand12 = ValueArithmeticOperand.of("3");
        operand2 = ItemArithmeticOperand.of("item2");
        operand21 = ItemArithmeticOperand.of("item21");

        expression12 = new FormulaExpression(null, operand12, null);
        expression11 = new FormulaExpression(Operator.DIV, operand11, expression12);
        expression1 = new FormulaExpression(Operator.ADD, operand1, expression11);
        expression21 = new FormulaExpression(null, operand21, null);
        expression2 = new FormulaExpression(Operator.MUL, operand2, expression21);

        formula = new ArithmeticFormula("formulaId", "targetItemId", itemBuilderFactory);
        formula.addContent(LocalDate.of(2018, 4, 1), expression1);
        formula.addContent(LocalDate.of(2018, 4, 18), expression2);

        executionContext = new NormalExecutionContext("dataId",
                Period.of("20180401", "20180430"),
                null, null);

        HrNumberItem item1 = new HrNumberItem("item1");
        item1.add(LocalDate.of(2018, 3, 20), new BigDecimal("300"));
        item1.add(LocalDate.of(2018, 4, 10), new BigDecimal("330"));
        item1.add(LocalDate.of(2018, 4, 20), new BigDecimal("360"));
        Mockito.when(itemBuilderFactory.getItem("item1")).thenReturn(item1);
        executionContext.add(item1);
        HrNumberItem item11 = new HrNumberItem("item11");
        item11.add(LocalDate.of(2018, 4, 1), new BigDecimal("200"));
        item11.add(LocalDate.of(2018, 4, 15), new BigDecimal("220"));
        Mockito.when(itemBuilderFactory.getItem("item11")).thenReturn(item11);
        executionContext.add(item11);
        HrNumberItem item2 = new HrNumberItem("item2");
        item2.add(LocalDate.of(2018, 4, 1), new BigDecimal("100"));
        item2.add(LocalDate.of(2018, 4, 30), new BigDecimal("110"));
        Mockito.when(itemBuilderFactory.getItem("item2")).thenReturn(item2);
        executionContext.add(item2);
        HrNumberItem item21 = new HrNumberItem("item21");
        item21.add(LocalDate.of(2018, 4, 1), new BigDecimal("50"));
        item21.add(LocalDate.of(2018, 4, 5), new BigDecimal("55"));
        item21.add(LocalDate.of(2018, 5, 5), new BigDecimal("60"));
        Mockito.when(itemBuilderFactory.getItem("item21")).thenReturn(item21);
        executionContext.add(item21);
    }

    @Test
    public void testGetRequiredItems() {
        Set<String> itemIds = this.formula.getRequiredItems();
        assertEquals(4, itemIds.size());
        assertEquals(true, itemIds.contains("item1"));
        assertEquals(true, itemIds.contains("item11"));
        assertEquals(true, itemIds.contains("item2"));
        assertEquals(true, itemIds.contains("item21"));
    }

    @Test
    public void testGetDateSegment() throws PayrollCoreException {
        SortedSet<LocalDate> dates = this.formula.getDateSegment(executionContext);
        assertEquals(7, dates.size());
        assertEquals(true, dates.contains(LocalDate.of(2018, 4, 1)));
        assertEquals(true, dates.contains(LocalDate.of(2018, 4, 5)));
        assertEquals(true, dates.contains(LocalDate.of(2018, 4, 10)));
        assertEquals(true, dates.contains(LocalDate.of(2018, 4, 15)));
        assertEquals(true, dates.contains(LocalDate.of(2018, 4, 18)));
        assertEquals(true, dates.contains(LocalDate.of(2018, 4, 20)));
        assertEquals(true, dates.contains(LocalDate.of(2018, 4, 30)));
    }

    @Test
    public void testEvaluate() throws PayrollCoreException {
        PaymentItem item = this.formula.evaluate(executionContext);
        assertEquals(true, item == result);
        assertEquals(5, item.getValues().size());
        assertEquals(new BigDecimal("366.6666666666666666666666666666667"), item.getValue(LocalDate.of(2018, 4, 1)));
        assertEquals(new BigDecimal("396.6666666666666666666666666666667"), item.getValue(LocalDate.of(2018, 4, 10)));
        assertEquals(new BigDecimal("403.3333333333333333333333333333333"), item.getValue(LocalDate.of(2018, 4, 15)));
        assertEquals(new BigDecimal("5500"), item.getValue(LocalDate.of(2018, 4, 18)));
        assertEquals(new BigDecimal("6050"), item.getValue(LocalDate.of(2018, 4, 30)));
    }
}