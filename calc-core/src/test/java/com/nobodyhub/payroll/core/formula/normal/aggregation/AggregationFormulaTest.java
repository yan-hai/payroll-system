package com.nobodyhub.payroll.core.formula.normal.aggregation;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.nobodyhub.payroll.core.common.Period;
import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.formula.common.FormulaTest;
import com.nobodyhub.payroll.core.item.payment.PaymentItem;
import com.nobodyhub.payroll.core.task.execution.normal.NormalExecutionContext;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * The aggregation formula tobe tested has 2 contents during the period of [20180401, 20180430]
 * 12356789012345678901234567890 ==== date in month
 * |----(1)------|----(2)------| ==== formula values
 * |100|--110----|*************| ==== values1, included in (1)
 * |--200---|-------220--------| ==== values2, included in (1)(2)
 * **************|300-|---330--| ==== values3, included in (2)
 * |300|310-|330-|520-|--550---| ==== result
 *
 * @author yan_h
 * @since 22/05/2018
 */
public class AggregationFormulaTest extends FormulaTest<AggregationFormula> {

    private PaymentItem item1;
    private PaymentItem item2;
    private PaymentItem item3;
    private PaymentItem resultItem;
    private Period period;


    @Override
    @Before
    public void setup() throws PayrollCoreException {
        super.setup();
        this.period = Period.of("20180401", "20180430");

        this.formula = new AggregationFormula("aggr_formula_id",
                "aggr_target_id",
                itemBuilderFactory);
        this.formula.addContent(LocalDate.of(2018, 4, 1),
                Sets.newHashSet("item1", "item2"));
        this.formula.addContent(LocalDate.of(2018, 4, 16),
                Sets.newHashSet("item2", "item3"));

        this.executionContext = new NormalExecutionContext("dataId",
                this.period,
                null,
                null);

        this.item1 = new PaymentItem("item1", false, null, null);
        this.item1.add(LocalDate.of(2018, 4, 1), new BigDecimal("100"));
        this.item1.add(LocalDate.of(2018, 4, 6), new BigDecimal("110"));
        this.executionContext.add(this.item1);

        this.item2 = new PaymentItem("item2", false, null, null);
        this.item2.add(LocalDate.of(2018, 4, 1), new BigDecimal("200"));
        this.item2.add(LocalDate.of(2018, 4, 11), new BigDecimal("220"));
        this.executionContext.add(this.item2);

        this.item3 = new PaymentItem("item3", false, null, null);
        this.item3.add(LocalDate.of(2018, 4, 1), new BigDecimal("300"));
        this.item3.add(LocalDate.of(2018, 4, 21), new BigDecimal("330"));
        this.executionContext.add(this.item3);

        this.resultItem = new PaymentItem("aggr_target_id", false, null, null);
        Mockito.when(this.itemBuilderFactory.getItem("aggr_target_id", PaymentItem.class)).thenReturn(resultItem);
        Mockito.when(this.itemBuilderFactory.getItem("aggr_target_id")).thenReturn(resultItem);
    }


    @Override
    public void testCompareTo() {
        AggregationFormula anoFormula = new AggregationFormula("aggr_formula_id",
                "aggr_target_id",
                itemBuilderFactory);
        assertEquals(0, this.formula.compareTo(anoFormula));
        anoFormula.setPriority(1001);
        assertEquals(-1, this.formula.compareTo(anoFormula));
        anoFormula.setPriority(999);
        assertEquals(1, this.formula.compareTo(anoFormula));
    }

    @Override
    @Test
    public void testEvaluate() throws PayrollCoreException {
        PaymentItem result = this.formula.evaluate(executionContext);
        assertEquals(5, result.getValues().size());
        assertEquals(new BigDecimal("300"), result.getValue(LocalDate.of(2018, 4, 1)));
        assertEquals(new BigDecimal("310"), result.getValue(LocalDate.of(2018, 4, 6)));
        assertEquals(new BigDecimal("330"), result.getValue(LocalDate.of(2018, 4, 11)));
        assertEquals(new BigDecimal("520"), result.getValue(LocalDate.of(2018, 4, 16)));
        assertEquals(new BigDecimal("550"), result.getValue(LocalDate.of(2018, 4, 21)));
        List<LocalDate> dateList = Lists.newArrayList(result.getValues().keySet());
        assertEquals(5, dateList.size());
        assertEquals(LocalDate.of(2018, 4, 1), dateList.get(0));
        assertEquals(LocalDate.of(2018, 4, 6), dateList.get(1));
        assertEquals(LocalDate.of(2018, 4, 11), dateList.get(2));
        assertEquals(LocalDate.of(2018, 4, 16), dateList.get(3));
        assertEquals(LocalDate.of(2018, 4, 21), dateList.get(4));
    }
}