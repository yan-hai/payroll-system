package com.nobodyhub.payroll.core.service.common;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.protobuf.ProtocolStringList;
import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.exception.PayrollCoreExceptionCode;
import com.nobodyhub.payroll.core.item.ItemFactory;
import com.nobodyhub.payroll.core.service.proto.PayrollCoreProtocol;
import com.nobodyhub.payroll.core.task.execution.retro.RetroExecutionContext;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.Set;

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
    private final List<Map<String, String>> histories;

    public HistoryData(String dataId, Map<String, PayrollCoreProtocol.History> data) throws PayrollCoreException {
        this.dataId = dataId;
        this.histories = parseMessage(data);
    }

    /**
     * add all data for one history execution
     *
     * @param data
     */
    public void addData(Map<String, String> data) {
        histories.add(data);
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
    public Map<String, PayrollCoreProtocol.History> toMessage() throws PayrollCoreException {
        Map<String, PayrollCoreProtocol.History> messages = Maps.newHashMap();
        if (histories.isEmpty()) {
            return messages;
        }
        if (!isValid()) {
            throw new PayrollCoreException(PayrollCoreExceptionCode.HISTORY_DATA_INVALID)
                    .addValue("histories", histories);
        }
        for (Map<String, String> valueMap : histories) {
            for (Map.Entry<String, String> entry : valueMap.entrySet()) {
                String itemId = entry.getKey();
                String itemVal = entry.getValue();
                PayrollCoreProtocol.History history = messages.get(itemId);
                if (history == null) {
                    history = PayrollCoreProtocol.History.newBuilder().setItemId(itemId).build();
                    messages.put(itemId, history);
                }
                history.getValueList().add(itemVal);
            }
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
    public List<RetroExecutionContext> toRetroContexts(ItemFactory itemFactory) throws PayrollCoreException {
        List<RetroExecutionContext> contexts = Lists.newArrayList();
        for (Map<String, String> history : histories) {
            RetroExecutionContext context = new RetroExecutionContext(dataId, itemFactory, period);
            context.addAll(history);
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
    private List<Map<String, String>> parseMessage(Map<String, PayrollCoreProtocol.History> data) throws PayrollCoreException {
        List<Map<String, String>> histories = Lists.newArrayList();
        if (isValid(data)) {
            throw new PayrollCoreException(PayrollCoreExceptionCode.HISTORY_DATA_INVALID)
                    .addValue("data", data);
        }
        for (String itemId : data.keySet()) {
            ProtocolStringList values = data.get(itemId).getValueList();
            if (histories.isEmpty()) {
                for (String value : values) {
                    Map<String, String> valueMap = Maps.newHashMap();
                    valueMap.put(itemId, value);
                    histories.add(valueMap);
                }
            } else if (histories.size() == data.size()) {
                for (int idx = 0; idx < data.size(); idx++) {
                    Map<String, String> valueMap = histories.get(idx);
                    valueMap.put(itemId, values.get(idx));
                }
            } else {
                throw new PayrollCoreException(PayrollCoreExceptionCode.HISTORY_DATA_MALFORMED)
                        .addValue("data", data);
            }

        }
        return histories;
    }

    /**
     * Whether current {@link this#histories} is valid or not, which means:
     * - the history values should contains the same set of items
     *
     * @return
     */
    private boolean isValid() {
        Set<String> itemIds = null;
        for (Map<String, String> map : histories) {
            if (itemIds == null) {
                itemIds = map.keySet();
            } else {
                if (itemIds.containsAll(map.keySet())
                        && map.keySet().containsAll(itemIds)) {
                    return false;
                }
            }
        }
        return itemIds != null;
    }

    /**
     * Whether the given message info is valid or not, which means:
     * - the size of history values for each item should be the same
     *
     * @param data
     * @return
     */
    private boolean isValid(Map<String, PayrollCoreProtocol.History> data) {
        int length = -1;
        for (PayrollCoreProtocol.History history : data.values()) {
            if (length == -1) {
                length = history.getValueList().size();
            } else {
                if (length != history.getValueList().size()) {
                    return false;
                }
            }
        }
        return length != -1;
    }
}
