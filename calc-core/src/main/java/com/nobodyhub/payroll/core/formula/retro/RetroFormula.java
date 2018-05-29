package com.nobodyhub.payroll.core.formula.retro;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.nobodyhub.payroll.core.common.Period;
import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.formula.common.Formula;
import com.nobodyhub.payroll.core.item.ItemBuilderFactory;
import com.nobodyhub.payroll.core.item.common.Item;
import com.nobodyhub.payroll.core.item.payment.PaymentItem;
import com.nobodyhub.payroll.core.task.execution.retro.HistoryData;
import com.nobodyhub.payroll.core.task.execution.retro.RetroExecutionContext;
import com.nobodyhub.payroll.core.task.execution.retro.RetroTaskExecution;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.nobodyhub.payroll.core.exception.PayrollCoreExceptionCode.RETRO_FORMULA_INVALID;

/**
 * Formula for Retroactive
 * <p>
 * The retro formula will output values to a payment item, which could be used in other formula
 *
 * @author Ryan
 */
@EqualsAndHashCode(callSuper = true)
public class RetroFormula extends Formula<Set<String>> {

    public RetroFormula(String id, String targetItemId, ItemBuilderFactory itemBuilderFactory) {
        super(id, targetItemId, itemBuilderFactory);
    }

    /**
     * Compare values in RetroExecutionContext,
     * which are calculated in {@link RetroTaskExecution#run} with the {@link HistoryData.PeriodData}
     * then aggregate the diff value to the target payment item.
     *
     * @param retroContexts list of past retro calculation result
     * @param normalPeriod  the period of related NormalExecutionContext
     * @return
     * @throws PayrollCoreException
     */
    public PaymentItem evaluate(List<RetroExecutionContext> retroContexts, Period normalPeriod) throws PayrollCoreException {
        validate();
        // from itemid to diff amounts
        Map<String, BigDecimal> diffValues = Maps.newHashMap();
        for (RetroExecutionContext retroCtx : retroContexts) {
            HistoryData.PeriodData periodData = retroCtx.getPeriodData();
            Set<String> retroItemIds = getContent(retroCtx.getPeriod());
            if (retroItemIds != null) {
                for (String itemId : retroItemIds) {
                    BigDecimal exist = diffValues.get(itemId) == null ?
                            BigDecimal.ZERO : diffValues.get(itemId);
                    // calc the diff between current value and history value
                    PaymentItem paymentItem = retroCtx.get(itemId, PaymentItem.class);
                    exist = exist.add(paymentItem.getFinalValue(retroCtx)
                            .subtract(periodData.getPayment(itemId, itemBuilderFactory)));
                    diffValues.put(itemId, exist);

                }
            }
        }
        BigDecimal rst = BigDecimal.ZERO;
        for (BigDecimal val : diffValues.values()) {
            rst = rst.add(val);
        }
        return createPaymentItem(normalPeriod.getStart(), rst);
    }

    /**
     * ensure the related items are retro-items and are payment item
     */
    @Override
    protected void validate() throws PayrollCoreException {
        super.validate();
        List<String> invalidIds = Lists.newArrayList();
        Set<String> itemIds = getRequiredItems();
        for (String itemId : itemIds) {
            Item item = itemBuilderFactory.getItem(itemId);
            if (item instanceof PaymentItem && ((PaymentItem) item).isRetro()) {
                // pass validate
            } else {
                invalidIds.add(itemId);
            }
        }
        if (invalidIds.isEmpty()) {
            return;
        }
        throw new PayrollCoreException(RETRO_FORMULA_INVALID)
                .addMessage(
                        String.format("Items with Ids are either not retro items or not payment items: [%s]",
                                Joiner.on(",").join(invalidIds))
                );
    }

    /**
     * Get involved item ids
     *
     * @return
     */
    @Override
    public Set<String> getRequiredItems() {
        Set<String> idSet = Sets.newHashSet();
        for (Set<String> ids : contents.values()) {
            idSet.addAll(ids);
        }
        return idSet;
    }


}
