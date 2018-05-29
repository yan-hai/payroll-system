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

    /**
     * Evaluate cases included in this CaseSet
     * <p>
     * Short-circuit evaluation applied.
     *
     * @param context
     * @param date
     * @return
     * @throws PayrollCoreException
     */
    public BigDecimal evaluate(ExecutionContext context, LocalDate date) throws PayrollCoreException {
        for (FormulaCase formulaCase : cases) {
            if (formulaCase.matches(context, date)) {
                return formulaCase.getValue();
            }
        }
        return defaultValue;
    }

    /**
     * Get date segments
     *
     * @param context
     * @return
     * @throws PayrollCoreException
     */
    public Set<LocalDate> getDateSegment(ExecutionContext context) throws PayrollCoreException {
        Set<LocalDate> dateSet = Sets.newHashSet();
        for (FormulaCase formulaCase : cases) {
            dateSet.addAll(formulaCase.getDateSegment(context));
        }
        return dateSet;
    }

    /**
     * Get involved item ids
     *
     * @return
     */
    public Set<String> getRequiredItems() {
        Set<String> itemIds = Sets.newHashSet();
        for (FormulaCase formulaCase : cases) {
            itemIds.addAll(formulaCase.getRequiredItems());
        }
        return itemIds;
    }

    /**
     * Add case to CaseSet
     *
     * @param formulaCase
     */
    public void addCase(FormulaCase formulaCase) {
        this.cases.add(formulaCase);
    }
}
