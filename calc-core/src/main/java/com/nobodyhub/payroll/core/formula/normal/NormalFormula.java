package com.nobodyhub.payroll.core.formula.normal;

import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.formula.common.Formula;
import com.nobodyhub.payroll.core.item.ItemBuilderFactory;
import com.nobodyhub.payroll.core.item.payment.PaymentItem;
import com.nobodyhub.payroll.core.task.execution.ExecutionContext;
import lombok.EqualsAndHashCode;

/**
 * NormalFormula applied to PayItems in order to get numeric value
 *
 * @author yan_h
 * @since 2018-05-04.
 */
@EqualsAndHashCode(callSuper = true)
public abstract class NormalFormula<CT> extends Formula<CT> {

    public NormalFormula(String id, String targetItemId, ItemBuilderFactory itemBuilderFactory) {
        super(id, targetItemId, itemBuilderFactory);
    }

    /**
     * Calculate the result value of applying the formula
     *
     * @param context context contains all items
     * @return result value of formula evaluated in the <code>context</code>
     * @throws PayrollCoreException
     */
    public abstract PaymentItem evaluate(ExecutionContext context) throws PayrollCoreException;

}
