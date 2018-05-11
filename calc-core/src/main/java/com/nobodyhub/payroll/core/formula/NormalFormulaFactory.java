package com.nobodyhub.payroll.core.formula;

import com.nobodyhub.payroll.core.formula.normal.NormalFormula;
import com.nobodyhub.payroll.core.item.ItemBuilderFactory;

/**
 * @author Ryan
 */
public abstract class NormalFormulaFactory extends FormulaFactory<NormalFormula> {
    public NormalFormulaFactory(ItemBuilderFactory itemBuilderFactory) {
        super(itemBuilderFactory);
    }
}
