package com.nobodyhub.payroll.core.formula.normal.map;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.task.execution.ExecutionContext;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/**
 * @author Ryan
 */
@RequiredArgsConstructor
public class FormulaCaseSet {
    /**
     * Cases which will be evaluated in order
     */
    protected final List<FormulaCase> cases = Lists.newArrayList();
    /**
     * Default value if no Case is matched
     */
    protected final BigDecimal defaultValue;

    public BigDecimal evaluate(ExecutionContext context, LocalDate date) throws PayrollCoreException {
        BigDecimal result = defaultValue;
        for (FormulaCase formulaCase : cases) {
            if (formulaCase.matches(context, date)) {
                result = formulaCase.getValue();
                break;
            }
        }
        return result;
    }

    public Set<LocalDate> getDateSplit(ExecutionContext context) throws PayrollCoreException {
        Set<LocalDate> dateSet = Sets.newHashSet();
        for(FormulaCase formulaCase: cases) {
            dateSet.addAll(formulaCase.getDateSplit(context));
        }
        return dateSet;
    }

     public Set<String> getRequiredItems() {
         Set<String> itemIds = Sets.newHashSet();
         for (FormulaCase formulaCase : cases) {
             formulaCase.getRequiredItems(itemIds);
         }
         return itemIds;
     }
}
