package com.nobodyhub.payroll.core.task;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.item.ItemFactory;
import com.nobodyhub.payroll.core.item.common.Item;
import lombok.Getter;
import lombok.Setter;

import java.math.MathContext;
import java.util.Map;
import java.util.Set;

import static com.nobodyhub.payroll.core.exception.PayrollCoreExceptionCode.CONTEXT_NOT_FOUND;

/**
 * @author Ryan
 */
public class ExecutionContext {
    /**
     * the context contains all items
     */
    protected Map<String, Item> items = Maps.newHashMap();
    /**
     * the context of the belonging task
     */
    @Setter
    @Getter
    protected TaskContext taskContext;
    /**
     * Execution Status
     */
    protected ExecutionStatus executionStatus = new ExecutionStatus();
    /**
     * the factory of all items
     */
    protected ItemFactory factory;

    public void add(Item item) {
        items.put(item.getItemId(), item);
    }

    @SuppressWarnings("unchecked")
    public <T> void add(String itemId, T value) throws PayrollCoreException {
        Item item = factory.getItem(itemId);
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
