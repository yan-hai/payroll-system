package com.nobodyhub.payroll.core.service.data;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.protobuf.ProtocolStringList;
import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.exception.PayrollCoreExceptionCode;
import com.nobodyhub.payroll.core.service.proto.PayrollCoreProtocol;

import java.util.List;
import java.util.Map;

/**
 * @author Ryan
 */
public class HistoryData {
    private final List<Map<String, String>> histories;

    public HistoryData() {
        this.histories = Lists.newArrayList();
    }

    public HistoryData(Map<String, PayrollCoreProtocol.History> data) throws PayrollCoreException {
        this.histories = parseMessage(data);
    }

    public void addDat(Map<String, String> data) {
        histories.add(data);
    }

    private Map<String, PayrollCoreProtocol.History> toMessage() throws PayrollCoreException {
        Map<String, PayrollCoreProtocol.History> messages = Maps.newHashMap();
        if (histories.isEmpty()) {
            return messages;
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
        //TODO: validate
        return messages;
    }

    private List<Map<String, String>> parseMessage(Map<String, PayrollCoreProtocol.History> data) throws PayrollCoreException {
        List<Map<String, String>> histories = Lists.newArrayList();
        for (String itemId : data.keySet()) {
            ProtocolStringList values = data.get(itemId).getValueList();
            if (histories.isEmpty()) {
                for (String value : values) {
                    Map<String, String> valueMap = Maps.newHashMap();
                    valueMap.put(itemId, value);
                    histories.add(valueMap);
                }
            } else if (histories.size() == data.size()) {
                for(int idx = 0; idx< data.size(); idx++) {
                    Map<String, String> valueMap = histories.get(idx);
                    valueMap.put(itemId, values.get(idx));
                }
            } else {
                throw new PayrollCoreException(PayrollCoreExceptionCode.HISTORY_DATE_MALFORMED)
                        .addValue("data", data);
            }

        }
        //TODO: validate
        return histories;
    }
}
