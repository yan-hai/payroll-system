package com.nobodyhub.payroll.core.formula.normal.arithmetic;

import com.nobodyhub.payroll.core.common.Period;
import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.formula.common.Operator;
import com.nobodyhub.payroll.core.formula.normal.arithmetic.operand.ItemOperand;
import com.nobodyhub.payroll.core.formula.normal.arithmetic.operand.ValueOperand;
import com.nobodyhub.payroll.core.formula.normal.arithmetic.operand.abstr.Operand;
import com.nobodyhub.payroll.core.item.hr.HrNumberItem;
import com.nobodyhub.payroll.core.item.payment.PaymentItem;
import com.nobodyhub.payroll.core.task.execution.ExecutionContext;
import com.nobodyhub.payroll.core.task.execution.normal.NormalExecutionContext;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Evaluate a formua which in <a herf="https://en.wikipedia.org/wiki/Polish_notation">Polish notation</a> is:
 * {@code × itemOperand1 + valueOperand itemOperand2}
 *
 * @author yan_h
 * @since 24/05/2018
 */
public class FormulaExpressionTest {
    private Operand valueOperand;
    private Operand itemOperand1;
    private Operand itemOperand2;
    private FormulaExpression expression1;
    private FormulaExpression expression2;
    private FormulaExpression expression3;
    private HrNumberItem hrNumberItem;
    private PaymentItem paymentItem;
    private ExecutionContext executionContext;

    @Before
    public void setup() throws PayrollCoreException {
        valueOperand = ValueOperand.of("123.45");
        itemOperand1 = ItemOperand.of("hrNumberItem");
        itemOperand2 = ItemOperand.of("paymentItem");
        expression3 = new FormulaExpression(null, itemOperand2, null);
        expression2 = new FormulaExpression(Operator.ADD, valueOperand, expression3);
        expression1 = new FormulaExpression(Operator.MUL, itemOperand1, expression2);

        hrNumberItem = new HrNumberItem("hrNumberItem");
        hrNumberItem.add(LocalDate.of(2018, 4, 1), new BigDecimal("100"));
        hrNumberItem.add(LocalDate.of(2018, 4, 10), new BigDecimal("110"));

        paymentItem = new PaymentItem("paymentItem", false, null, null);
        paymentItem.add(LocalDate.of(2018, 4, 1), new BigDecimal("200"));
        paymentItem.add(LocalDate.of(2018, 4, 20), new BigDecimal("220"));

        executionContext = new NormalExecutionContext("dataId",
                Period.of("20180401", "20180430"),
                null, null);
        executionContext.add(hrNumberItem);
        executionContext.add(paymentItem);
    }

    @Test
    public void testEvaluate() throws PayrollCoreException {
        assertEquals(new BigDecimal("32345.00"),
                this.expression1.evaluate(executionContext, LocalDate.of(2018, 4, 2)));
        assertEquals(new BigDecimal("35579.50"),
                this.expression1.evaluate(executionContext, LocalDate.of(2018, 4, 11)));
        assertEquals(new BigDecimal("37779.50"),
                this.expression1.evaluate(executionContext, LocalDate.of(2018, 4, 21)));
    }

    @Test
    public void testGetRequiredItems() {
        Set<String> ids = this.expression1.getRequiredItems();
        assertEquals(2, ids.size());
        assertEquals(true, ids.contains("hrNumberItem"));
        assertEquals(true, ids.contains("paymentItem"));
    }

    @Test
    public void testGetDateSegment() throws PayrollCoreException {
        Set<LocalDate> dates = this.expression1.getDateSegment(executionContext);
        assertEquals(3, dates.size());
        assertEquals(true, dates.contains(LocalDate.of(2018, 4, 1)));
        assertEquals(true, dates.contains(LocalDate.of(2018, 4, 10)));
        assertEquals(true, dates.contains(LocalDate.of(2018, 4, 20)));
    }
}