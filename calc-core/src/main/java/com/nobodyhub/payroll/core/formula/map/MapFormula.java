package com.nobodyhub.payroll.core.formula.map;

import com.google.common.collect.Lists;
import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.formula.common.Formula;
import com.nobodyhub.payroll.core.item.ItemContext;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * A Map form of formula, it maps the conditions to a given value.
 * - CASE 1 => A
 * - CASE 2 => B
 * - CASE 3 => C
 * <p>
 * CASE 1~3 will be evaluated in order, whenever case evaluated as true,
 * corresponding value will be returned immediately without evaluate the remaining CASEs.
 *
 * @author Ryan
 * @since 2018-05-04.
 */
@RequiredArgsConstructor
public class MapFormula extends Formula {
    /**
     * Cases of the map
     */
    protected final List<FormulaCase> cases = Lists.newArrayList();
    /**
     * Default value if no Case is matched
     */
    protected final BigDecimal defaultValue;

    @Override
    public BigDecimal evaluate(ItemContext context) throws PayrollCoreException {
        for (FormulaCase formulaCase : cases) {
            if (formulaCase.evaluate(context)) {
                return formulaCase.getValue();
            }
        }
        return defaultValue;
    }
}
