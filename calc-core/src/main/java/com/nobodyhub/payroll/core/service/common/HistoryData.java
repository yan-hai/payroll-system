package com.nobodyhub.payroll.core.service.common;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.protobuf.ProtocolStringList;
import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.exception.PayrollCoreExceptionCode;
import com.nobodyhub.payroll.core.service.proto.PayrollCoreProtocol;
import com.nobodyhub.payroll.core.task.execution.context.RetroExecutionContext;
import com.nobodyhub.payroll.core.task.TaskContext;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Ryan
 */
@Getter
public class HistoryData {
    private final List<Map<String, String>> histories;

    public HistoryData() {
        this.histories = Lists.newArrayList();
    }

    public HistoryData(Map<String, PayrollCoreProtocol.History> data) throws PayrollCoreException {
        this.histories = parseMessage(data);
    }

    public void addData(Map<String, String> data) {
        histories.add(data);
    }

    public boolean isEmpty() {
        return histories.isEmpty();
    }

    private Map<String, PayrollCoreProtocol.History> toMessage() throws PayrollCoreException {
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

    public List<RetroExecutionContext> toRetroContexts(String dataId, TaskContext taskContext) throws PayrollCoreException {
        List<RetroExecutionContext> contexts = Lists.newArrayList();
        for (Map<String, String> history : histories) {
            RetroExecutionContext context = new RetroExecutionContext(dataId, taskContext);
            context.addAll(history);
            contexts.add(context);
        }
        return contexts;
    }

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
     * the history values should contains the same set of items
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
     * the size of history values for each item should be the same
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
