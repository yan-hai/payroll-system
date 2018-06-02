package com.nobodyhub.payroll.core.example.facade;

import com.nobodyhub.payroll.core.formula.NormalFormulaFactory;
import com.nobodyhub.payroll.core.formula.normal.map.MapFormula;
import com.nobodyhub.payroll.core.item.ItemBuilderFactory;

import static com.nobodyhub.payroll.core.example.facade.ExampleConst.FOR_DAILY_SALARY;
import static com.nobodyhub.payroll.core.example.facade.ExampleConst.PAY_DAILY_SALARY;

/**
 * @author yan_h
 * @since 2018-05-11
 */
public class ExampleNormalFormulaFactory extends NormalFormulaFactory {
    public ExampleNormalFormulaFactory(ItemBuilderFactory itemBuilderFactory) {
        super(itemBuilderFactory);
        initContents();
    }

    /**
     * initialize the contents
     */
    @Override
    public void initContents() {
        add(new MapFormula(FOR_DAILY_SALARY, PAY_DAILY_SALARY, itemBuilderFactory));
    }
}
