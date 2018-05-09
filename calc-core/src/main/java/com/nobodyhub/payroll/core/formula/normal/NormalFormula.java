package com.nobodyhub.payroll.core.formula.normal;

import com.google.common.collect.Maps;
import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.formula.common.Formula;
import com.nobodyhub.payroll.core.item.payment.PaymentItem;
import com.nobodyhub.payroll.core.task.execution.ExecutionContext;

import java.time.LocalDate;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;

/**
 * NormalFormula applied to PayItems in order to get numeric value
 *
 * @param <T> formula Content type
 * @author yan_h
 * @since 2018-05-04.
 */
public abstract class NormalFormula<T> extends Formula {
    /**
     * Contents of formula
     */
    protected SortedMap<LocalDate, T> contents = Maps.newTreeMap();

    /**
     * Calculate the result value of applying the formula
     *
     * @param context context contains all items
     * @return result value of formula evaluated in the <code>context</code>
     * @throws PayrollCoreException
     */
    public abstract PaymentItem evaluate(ExecutionContext context) throws PayrollCoreException;
}
