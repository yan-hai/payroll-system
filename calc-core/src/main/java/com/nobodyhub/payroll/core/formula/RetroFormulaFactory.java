package com.nobodyhub.payroll.core.formula;

import com.nobodyhub.payroll.core.formula.retro.RetroFormula;
import com.nobodyhub.payroll.core.item.ItemBuilderFactory;

/**
 * @author Ryan
 */
public abstract class RetroFormulaFactory extends FormulaFactory<RetroFormula> {

    protected RetroFormulaFactory(ItemBuilderFactory itemBuilderFactory) {
        super(itemBuilderFactory);
    }
}
