package com.nobodyhub.payroll.core.formula.normal;

import com.google.common.collect.Maps;
import com.nobodyhub.payroll.core.common.Period;
import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.formula.common.Formula;
import com.nobodyhub.payroll.core.item.ItemBuilderFactory;
import com.nobodyhub.payroll.core.item.payment.PaymentItem;
import com.nobodyhub.payroll.core.task.execution.ExecutionContext;

import java.time.LocalDate;
import java.util.SortedMap;

/**
 * NormalFormula applied to PayItems in order to get numeric value
 *
 * @param <CT> type of formula content
 * @author yan_h
 * @since 2018-05-04.
 */
public abstract class NormalFormula<CT> extends Formula {
    /**
     * Contents of formula
     * the keys are sorted from the latest to the oldest
     */
    protected final SortedMap<LocalDate, CT> contents = Maps.newTreeMap((o1, o2) -> (o1.compareTo(o2) * (-1)));

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

    /**
     * add content
     *
     * @param date
     * @param value
     */
    public void addContent(LocalDate date, CT value) {
        contents.put(date, value);
    }

    /**
     * get the valid content for given date
     *
     * @param basedate
     * @return
     */
    public CT getContent(LocalDate basedate) {
        for (LocalDate date : contents.keySet()) {
            if (date.compareTo(basedate) <= 0) {
                return contents.get(date);
            }
        }
        return null;
    }

    /**
     * get the valid content for given periods
     *
     * @param period
     * @return
     */
    public CT getContent(Period period) {
        return getContent(period.getStart());
    }
}
