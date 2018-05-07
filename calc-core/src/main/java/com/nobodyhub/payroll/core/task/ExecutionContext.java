package com.nobodyhub.payroll.core.task;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.item.ItemFactory;
import com.nobodyhub.payroll.core.item.common.Item;
import com.nobodyhub.payroll.core.service.proto.CalculationCoreProtocol;
import com.nobodyhub.payroll.core.task.status.ExecutionStatus;
import lombok.Getter;

import java.math.MathContext;
import java.util.Map;
import java.util.Set;

import static com.nobodyhub.payroll.core.exception.PayrollCoreExceptionCode.CONTEXT_NOT_FOUND;

/**
 * @author Ryan
 */
@Getter
public class ExecutionContext {
    /**
     * the identifier of the data in <code>items</code>
     */
    protected final String dataId;
    /**
     * the context contains all items
     */
    protected final Map<String, Item> items = Maps.newHashMap();
    /**
     * the context of the belonging task
     */
    protected final TaskContext taskContext;
    /**
     * Execution Status
     */
    protected final ExecutionStatus executionStatus = new ExecutionStatus();
    /**
     * the factory of all items
     */
    protected final ItemFactory itemFactory;

    public ExecutionContext(String dataId, TaskContext taskContext) {
        this.dataId = dataId;
        this.taskContext = taskContext;
        this.itemFactory = taskContext.getItemFactory();
    }


    public void add(Item item) {
        items.put(item.getItemId(), item);
    }

    public void addAll(Map<String, String> valueMap) throws PayrollCoreException {
        for (Map.Entry<String, String> entry : valueMap.entrySet()) {
            String itemId = entry.getKey();
            String value = entry.getValue();
            Item item = itemFactory.getItem(itemId);
            item.setStringValue(value);
        }
    }

    public CalculationCoreProtocol.Response toResponse() {
        Map<String, String> values = Maps.newHashMap();
        for (Item item : items.values()) {
            values.put(item.getItemId(), item.getValueAsString());
        }
        return CalculationCoreProtocol.Response.newBuilder()
                .setStatusCode(executionStatus.getStatusCode().toString())
                .setMessage(executionStatus.getMessage())
                .setDataId(dataId)
                .putAllValues(values)
                .build();
    }

    @SuppressWarnings("unchecked")
    public <T> void add(String itemId, T value) throws PayrollCoreException {
        Item item = itemFactory.getItem(itemId);
        item.setValue(value);
        items.put(item.getItemId(), item);
    }

    public Item get(String itemId) throws PayrollCoreException {
        Item item = items.get(itemId);
        if (item == null) {
            throw new PayrollCoreException(CONTEXT_NOT_FOUND)
                    .addValue("itemId", itemId);
        }
        return item;
    }

    public Set<String> getAllItemIds() {
        return Sets.newHashSet(items.keySet());
    }

    public MathContext getMathContext() {
        return taskContext.getMathContext();
    }

    /**
     * print a json-like string
     *
     * @return
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{ \"name\": \"ExecutionContext(size: " + items.size() + ")\", context: { ");
        for (Item item : items.values()) {
            sb.append("\t\"" + item.getItemId() + "\" : \"" + item.getValue().toString() + "\"");
        }
        sb.append("}}");
        return sb.toString();
    }
}
