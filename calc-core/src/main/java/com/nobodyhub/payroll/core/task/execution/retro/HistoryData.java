package com.nobodyhub.payroll.core.task.execution.retro;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.item.ItemBuilderFactory;
import com.nobodyhub.payroll.core.item.calendar.Period;
import com.nobodyhub.payroll.core.item.payment.PaymentItem;
import com.nobodyhub.payroll.core.proration.ProrationFactory;
import com.nobodyhub.payroll.core.service.proto.PayrollCoreProtocol;
import com.nobodyhub.payroll.core.util.DateFormatUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * The history data provided for the payroll task to perform retroactive calculation
 *
 * @author Ryan
 */
@Getter
public class HistoryData {
    /**
     * identifier of the owner of data to be executed
     */
    private final String dataId;
    /**
     * factory to get new instance of item
     */
    private final ItemBuilderFactory itemBuilderFactory;
    /**
     * a list of history data, could be HR data, attendence data, payment data, etc.
     * each map element contains the data for one history execution, from ItemId to its value
     */
    private final Map<Period, PeriodData> histories;

    public HistoryData(String dataId, ItemBuilderFactory itemBuilderFactory, List<PayrollCoreProtocol.PeriodValue> data) throws PayrollCoreException {
        this.dataId = dataId;
        this.itemBuilderFactory = itemBuilderFactory;
        this.histories = parseMessage(data);
    }

    /**
     * whether history data is empty or not
     *
     * @return
     */
    public boolean isEmpty() {
        return histories.isEmpty();
    }

    /**
     * convert history data to format used in gRPC message
     *
     * @return
     * @throws PayrollCoreException
     */
    public List<PayrollCoreProtocol.PeriodValue> toMessage() throws PayrollCoreException {
        List<PayrollCoreProtocol.PeriodValue> messages = Lists.newArrayList();
        if (isEmpty()) {
            return messages;
        }
        for (Period period : histories.keySet()) {
            messages.add(PayrollCoreProtocol.PeriodValue.newBuilder()
                    .setStartDate(DateFormatUtils.convertDate(period.getStart()))
                    .setEndDate(DateFormatUtils.convertDate(period.getEnd()))
                    .addAllItems(histories.get(period).toMessage())
                    .build());
        }
        return messages;
    }

    /**
     * Generate contexts for Retroactive calculation
     *
     * @param itemBuilderFactory factorty to create items
     * @return
     * @throws PayrollCoreException
     */
    public List<RetroExecutionContext> toRetroContexts(ItemBuilderFactory itemBuilderFactory, ProrationFactory prorationFactory) throws PayrollCoreException {
        List<RetroExecutionContext> contexts = Lists.newArrayList();
        for (Period period : histories.keySet()) {
            RetroExecutionContext context = new RetroExecutionContext(dataId,
                    itemBuilderFactory,
                    prorationFactory,
                    histories.get(period)
            );
            context.addAll(histories.get(period));
            contexts.add(context);
        }
        return contexts;
    }

    /**
     * parse the contents, {@link this#histories}, from gRPC message
     *
     * @param data message data
     * @return
     * @throws PayrollCoreException
     */
    private Map<Period, PeriodData> parseMessage(List<PayrollCoreProtocol.PeriodValue> data) throws PayrollCoreException {
        Map<Period, PeriodData> histories = Maps.newHashMap();
        for (PayrollCoreProtocol.PeriodValue periodValue : data) {
            Period period = Period.of(periodValue.getStartDate(), periodValue.getEndDate(), periodValue.getBaseDate());
            PeriodData periodData = new PeriodData(period);
            for (PayrollCoreProtocol.ItemValue itemValue : periodValue.getItemsList()) {
                periodData.add(itemValue);
            }
            histories.put(period, periodData);
        }
        return histories;
    }

    @RequiredArgsConstructor
    @Getter
    public static class PeriodData {
        private final Period period;
        private final Map<String, Map<String, String>> itemValues = Maps.newHashMap();

        public void add(PayrollCoreProtocol.ItemValue itemValue) {
            itemValues.put(itemValue.getItemId(), itemValue.getValuesMap());
        }

        public List<PayrollCoreProtocol.ItemValue> toMessage() {
            List<PayrollCoreProtocol.ItemValue> values = Lists.newArrayList();
            for (Map.Entry<String, Map<String, String>> entry : this.itemValues.entrySet()) {
                values.add(PayrollCoreProtocol.ItemValue.newBuilder()
                        .setItemId(entry.getKey())
                        .putAllValues(entry.getValue())
                        .build());
            }
            return values;
        }

        public BigDecimal getPayment(String itemId, ItemBuilderFactory itemBuilderFactory) throws PayrollCoreException {
            //verify the item related to itemId is PaymentItem
            itemBuilderFactory.getItem(itemId, PaymentItem.class);
            BigDecimal finalVal = BigDecimal.ZERO;
            for (String value : itemValues.get(itemId).values()) {
                finalVal = finalVal.add(new BigDecimal(value));
            }
            return finalVal;
        }
    }
}
