package com.nobodyhub.payroll.core.formula.normal.map;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.formula.normal.Formula;
import com.nobodyhub.payroll.core.item.payment.PaymentItem;
import com.nobodyhub.payroll.core.task.ExecutionContext;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

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
    public PaymentItem evaluate(ExecutionContext context) throws PayrollCoreException {
        BigDecimal result = defaultValue;
        for (FormulaCase formulaCase : cases) {
            if (formulaCase.evaluate(context)) {
                result = formulaCase.getValue();
                break;
            }
        }
        PaymentItem item = createPaymentItem();
        item.setValue(result);
        return item;
    }

    @Override
    public Set<String> getRequiredItems() {
        Set<String> itemIds = Sets.newHashSet();
        for (FormulaCase formulaCase : cases) {
            formulaCase.getRequiredItems(itemIds);
        }
        return itemIds;
    }
}
