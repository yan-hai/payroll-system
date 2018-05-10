package com.nobodyhub.payroll.core.service.common;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.item.ItemFactory;
import com.nobodyhub.payroll.core.item.calendar.Period;
import com.nobodyhub.payroll.core.proration.ProrationContainer;
import com.nobodyhub.payroll.core.service.proto.PayrollCoreProtocol;
import com.nobodyhub.payroll.core.task.execution.retro.RetroExecutionContext;
import com.nobodyhub.payroll.core.util.DateFormatUtils;
import lombok.Getter;

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
     * a list of history data, could be HR data, attendence data, payment data, etc.
     * each map element contains the data for one history execution, from ItemId to its value
     */
    private final Map<Period, List<PayrollCoreProtocol.ItemValue>> histories;

    public HistoryData(String dataId, List<PayrollCoreProtocol.PeriodValue> data) throws PayrollCoreException {
        this.dataId = dataId;
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
                    .addAllItems(histories.get(period))
                    .build());
        }
        return messages;
    }

    /**
     * Generate contexts for Retroactive calculation
     *
     * @param itemFactory factorty to create items
     * @return
     * @throws PayrollCoreException
     */
    public List<RetroExecutionContext> toRetroContexts(ItemFactory itemFactory, ProrationContainer prorationContainer) throws PayrollCoreException {
        List<RetroExecutionContext> contexts = Lists.newArrayList();
        for (Period period : histories.keySet()) {
            RetroExecutionContext context = new RetroExecutionContext(dataId, itemFactory, period, prorationContainer);
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
    private Map<Period, List<PayrollCoreProtocol.ItemValue>> parseMessage(List<PayrollCoreProtocol.PeriodValue> data) throws PayrollCoreException {
        Map<Period, List<PayrollCoreProtocol.ItemValue>> histories = Maps.newHashMap();
        for (PayrollCoreProtocol.PeriodValue periodValue : data) {
            Period period = Period.of(periodValue.getStartDate(), periodValue.getEndDate());
            histories.put(period, periodValue.getItemsList());
        }
        return histories;
    }
}
