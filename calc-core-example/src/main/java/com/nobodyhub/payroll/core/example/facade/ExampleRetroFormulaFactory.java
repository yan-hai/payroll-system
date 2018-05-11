package com.nobodyhub.payroll.core.example.facade;

import com.nobodyhub.payroll.core.formula.RetroFormulaFactory;
import com.nobodyhub.payroll.core.formula.retro.RetroFormula;
import com.nobodyhub.payroll.core.item.ItemBuilderFactory;

/**
 * @author yan_h
 * @since 2018-05-11
 */
public class ExampleRetroFormulaFactory extends RetroFormulaFactory {
    public ExampleRetroFormulaFactory(ItemBuilderFactory itemBuilderFactory) {
        super(itemBuilderFactory);
    }

    /**
     * initialize the contents
     */
    @Override
    public void initContents() {
        add(new RetroFormula("RetroFormula_1", "Payment_1", itemBuilderFactory));
    }
}
