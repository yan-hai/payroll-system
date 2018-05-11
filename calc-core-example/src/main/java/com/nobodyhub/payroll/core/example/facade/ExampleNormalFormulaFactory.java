package com.nobodyhub.payroll.core.example.facade;

import com.nobodyhub.payroll.core.formula.NormalFormulaFactory;
import com.nobodyhub.payroll.core.formula.normal.map.MapFormula;
import com.nobodyhub.payroll.core.item.ItemBuilderFactory;

/**
 * @author yan_h
 * @since 2018-05-11
 */
public class ExampleNormalFormulaFactory extends NormalFormulaFactory {
    public ExampleNormalFormulaFactory(ItemBuilderFactory itemBuilderFactory) {
        super(itemBuilderFactory);
    }

    /**
     * initialize the contents
     */
    @Override
    public void initContents() {
        add(new MapFormula("RetroFormula_1", "Payment_1", itemBuilderFactory));
    }
}
