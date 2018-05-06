package com.nobodyhub.payroll.core.item;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.item.abstr.Item;
import com.nobodyhub.payroll.core.task.TaskContext;

import java.util.Map;
import java.util.Set;

import static com.nobodyhub.payroll.core.exception.PayrollCoreExceptionCode.CONTEXT_NOT_FOUND;

/**
 * @author Ryan
 */
public class ItemContext {
    /**
     * the context contains all items
     */
    private Map<String, Item> context = Maps.newHashMap();
    /**
     * the context of the belonging task
     */
    private TaskContext taskContext;
    /**
     * the factory of all items
     */
    private ItemFactory factory;

    public void add(Item item) {
        context.put(item.getItemId(), item);
    }

    public <T> void add(String itemId, T value) throws PayrollCoreException {
        Item item = factory.getItem(itemId);
        item.setValue(value);
        context.put(item.getItemId(), item);
    }

    public Item get(String itemId) throws PayrollCoreException {
        Item item = context.get(itemId);
        if (item == null) {
            throw new PayrollCoreException(CONTEXT_NOT_FOUND)
                    .addValue("itemId", itemId);
        }
        return item;
    }

    public Set<String> getAllItemIds() {
        return Sets.newHashSet(context.keySet());
    }

    /**
     * print a json-like string
     *
     * @return
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{ \"name\": \"ItemContext(size: " + context.size() + ")\", context: { ");
        for (Item item : context.values()) {
            sb.append("\t\"" + item.getItemId() + "\" : \"" + item.getValue().toString() + "\"");
        }
        sb.append("}}");
        return sb.toString();
    }
}
