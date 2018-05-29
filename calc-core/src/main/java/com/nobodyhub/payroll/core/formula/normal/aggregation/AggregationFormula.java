package com.nobodyhub.payroll.core.formula.normal.aggregation;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.nobodyhub.payroll.core.common.Period;
import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.formula.normal.NormalFormula;
import com.nobodyhub.payroll.core.item.ItemBuilderFactory;
import com.nobodyhub.payroll.core.item.common.Item;
import com.nobodyhub.payroll.core.item.payment.PaymentItem;
import com.nobodyhub.payroll.core.task.execution.ExecutionContext;
import com.nobodyhub.payroll.core.util.PeriodUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.nobodyhub.payroll.core.exception.PayrollCoreExceptionCode.RETRO_FORMULA_INVALID;

/**
 * Aggregate a list of payitems
 * <p>
 * the {@link this#contents} are a list of ids of payment items that would be aggregated to the result
 *
 * @author yan_h
 */
public class AggregationFormula extends NormalFormula<Set<String>> {

    public AggregationFormula(String id, String targetItemId, ItemBuilderFactory itemBuilderFactory) {
        super(id, targetItemId, itemBuilderFactory);
    }

    @Override
    public PaymentItem evaluate(ExecutionContext context) throws PayrollCoreException {
        // get the finest-grained period from all dates involved in formula
        Set<LocalDate> dates = Sets.newHashSet(contents.keySet());
        for (Map.Entry<LocalDate, Set<String>> entry : contents.entrySet()) {
            Set<String> itemIdSet = entry.getValue();
            for (String itemId : itemIdSet) {
                PaymentItem item = context.get(itemId, PaymentItem.class);
                dates.addAll(item.getValues().keySet());
            }
        }
        List<Period> periods = PeriodUtils.convertDateToPeriod(dates, context.getPeriod());
        // get values for each period
        Map<LocalDate, BigDecimal> results = Maps.newHashMap();
        for (Period period : periods) {
            Set<String> itemIdSet = getContent(period);
            BigDecimal existingValue = results.get(period.getStart()) == null ?
                    BigDecimal.ZERO : results.get(period.getStart());
            // aggregate values for that period
            for (String itemId : itemIdSet) {
                BigDecimal value = context.getItemValue(itemId, period, BigDecimal.class);
                existingValue = existingValue.add(value);
            }
            results.put(period.getStart(), existingValue);
        }
        return createPaymentItem(results);
    }

    @Override
    public Set<String> getRequiredItems() {
        Set<String> idSet = Sets.newHashSet();
        for (Set<String> ids : contents.values()) {
            idSet.addAll(ids);
        }
        return idSet;
    }

    @Override
    protected void validate() throws PayrollCoreException {
        super.validate();
        List<String> invalidIds = Lists.newArrayList();
        Set<String> itemIds = getRequiredItems();
        for (String itemId : itemIds) {
            Item item = itemBuilderFactory.getItem(itemId);
            if (item instanceof PaymentItem) {
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
                        String.format("Items with following ids are not payment items: [%s]",
                                Joiner.on(",").join(invalidIds))
                );
    }
}
