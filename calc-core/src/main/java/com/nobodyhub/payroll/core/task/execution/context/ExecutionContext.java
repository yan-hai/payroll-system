package com.nobodyhub.payroll.core.task.execution.context;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.item.ItemFactory;
import com.nobodyhub.payroll.core.item.common.Item;
import com.nobodyhub.payroll.core.service.proto.PayrollCoreProtocol;
import com.nobodyhub.payroll.core.task.TaskContext;
import com.nobodyhub.payroll.core.task.status.ExecutionStatus;
import lombok.Getter;

import java.util.Map;
import java.util.Set;

import static com.nobodyhub.payroll.core.exception.PayrollCoreExceptionCode.CONTEXT_NOT_FOUND;

/**
 * Context of the calculation execution for non-retroactive calculation
 *
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

    /**
     * add new item to the context
     *
     * @param item
     */
    public void add(Item item) {
        items.put(item.getItemId(), item);
    }

    /**
     * add all values as items into the context
     *
     * @param valueMap
     * @throws PayrollCoreException
     */
    public void addAll(Map<String, String> valueMap) throws PayrollCoreException {
        for (Map.Entry<String, String> entry : valueMap.entrySet()) {
            String itemId = entry.getKey();
            String value = entry.getValue();
            Item item = itemFactory.getItem(itemId);
            item.setStringValue(value);
        }
    }

    /**
     * convert the content to the response to client
     *
     * @return
     */
    public PayrollCoreProtocol.Response toResponse() {
        Map<String, String> values = Maps.newHashMap();
        for (Item item : items.values()) {
            values.put(item.getItemId(), item.getValueAsString());
        }
        return PayrollCoreProtocol.Response.newBuilder()
                .setStatusCode(executionStatus.getStatusCode().toString())
                .setMessage(executionStatus.getMessage())
                .setDataId(dataId)
                .putAllValues(values)
                .build();
    }

    /**
     * get item from the context for given itemId
     *
     * @param itemId
     * @return
     * @throws PayrollCoreException
     */
    public Item get(String itemId) throws PayrollCoreException {
        Item item = items.get(itemId);
        if (item == null) {
            throw new PayrollCoreException(CONTEXT_NOT_FOUND)
                    .addValue("itemId", itemId);
        }
        return item;
    }

    /**
     * get ids of all items in the context
     *
     * @return
     */
    public Set<String> getAllItemIds() {
        return Sets.newHashSet(items.keySet());
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
