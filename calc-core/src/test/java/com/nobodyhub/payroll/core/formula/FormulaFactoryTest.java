package com.nobodyhub.payroll.core.formula;

import com.google.common.collect.Sets;
import com.nobodyhub.payroll.core.formula.normal.NormalFormula;
import com.nobodyhub.payroll.core.formula.normal.aggregation.AggregationFormula;
import com.nobodyhub.payroll.core.item.ItemBuilderFactory;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static junit.framework.TestCase.assertEquals;

/**
 * @author yan_h
 * @since 2018/6/3
 */
public class FormulaFactoryTest {
    @Mock
    private ItemBuilderFactory itemBuilderFactory;


    private AggregationFormula formula1;
    private AggregationFormula formula2;
    private AggregationFormula formula3;
    private AggregationFormula formula4;
    private AggregationFormula formula5;


    private NormalFormulaFactory normalFormulaFactory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        // use UUID as key to get random orde when call Map.values()
        formula1 = new AggregationFormula(
                UUID.randomUUID().toString(),
                "item1",
                itemBuilderFactory);
        formula1.addContent(LocalDate.of(2018, 6, 1),
                Sets.newHashSet());

        formula2 = new AggregationFormula(
                UUID.randomUUID().toString(),
                "item2",
                itemBuilderFactory);
        formula2.addContent(LocalDate.of(2018, 6, 1),
                Sets.newHashSet(
                        "item1"
                ));

        formula3 = new AggregationFormula(
                UUID.randomUUID().toString(),
                "item3",
                itemBuilderFactory);
        formula3.addContent(LocalDate.of(2018, 6, 1),
                Sets.newHashSet(
                        "item1", "item2"
                ));

        formula4 = new AggregationFormula(
                UUID.randomUUID().toString(),
                "item4",
                itemBuilderFactory);
        formula4.addContent(LocalDate.of(2018, 6, 1),
                Sets.newHashSet(
                        "item3"
                ));

        formula5 = new AggregationFormula(
                UUID.randomUUID().toString(),
                "item5",
                itemBuilderFactory);
        formula5.addContent(LocalDate.of(2018, 6, 1),
                Sets.newHashSet(
                        "item1", "item2"
                ));

        normalFormulaFactory = new NormalFormulaFactory(itemBuilderFactory) {
            @Override
            public void initContents() {
                add(formula5);
                add(formula4);
                add(formula2);
                add(formula1);
                add(formula3);
            }
        };
    }

    @Test
    public void testGetPrioritizedFormulas() {
        List<NormalFormula> results = normalFormulaFactory.getPrioritizedFormulas();
        assertEquals(5, results.size());
        assertEquals(formula1, results.get(0));
        assertEquals(formula2, results.get(1));
        assertEquals(formula3, results.get(2));
        assertEquals(formula4, results.get(3));
        assertEquals(formula5, results.get(4));
    }
}