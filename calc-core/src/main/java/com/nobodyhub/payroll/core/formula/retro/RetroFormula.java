package com.nobodyhub.payroll.core.formula.retro;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.formula.common.Formula;
import com.nobodyhub.payroll.core.item.calendar.Period;
import com.nobodyhub.payroll.core.item.common.Item;
import com.nobodyhub.payroll.core.item.payment.PaymentItem;
import com.nobodyhub.payroll.core.task.execution.retro.HistoryData;
import com.nobodyhub.payroll.core.task.execution.retro.RetroExecutionContext;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Ryan
 */
public class RetroFormula extends Formula {
    protected Set<String> relatedItemIds = Sets.newHashSet();

    /**
     * @param retroContexts list of past retro calculation result
     * @param normalPeriod  the period of related NormalExecutionContext
     * @return
     * @throws PayrollCoreException
     */
    public PaymentItem evaluate(List<RetroExecutionContext> retroContexts, Period normalPeriod) throws PayrollCoreException {
        Map<String, BigDecimal> diffValues = Maps.newHashMap();
        for (RetroExecutionContext retroCtx : retroContexts) {
            HistoryData.PeriodData periodData = retroCtx.getPeriodData();
            for (String itemId : relatedItemIds) {
                Item item = retroCtx.get(itemId);
                if ((item instanceof PaymentItem)
                        && ((PaymentItem) item).isRetro()) {
                    PaymentItem paymentItem = (PaymentItem) item;
                    BigDecimal exist = diffValues.get(itemId) == null ?
                            BigDecimal.ZERO : diffValues.get(itemId);
                    exist = exist.add(paymentItem.getFinalValue(retroCtx)
                            .subtract(periodData.getPayment(itemId, itemFactory)));
                    diffValues.put(itemId, exist);
                } else {
                    //TODO: log skip non-payment item or non-retro item
                }
            }
        }
        BigDecimal rst = BigDecimal.ZERO;
        for (BigDecimal val : diffValues.values()) {
            rst = rst.add(val);
        }
        return createPaymentItem(normalPeriod.getStart(), rst);
    }

    @Override
    public Set<String> getRequiredItems() {
        return new HashSet<>(relatedItemIds);
    }
}
