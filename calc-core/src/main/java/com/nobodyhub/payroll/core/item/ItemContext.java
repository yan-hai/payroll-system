package com.nobodyhub.payroll.core.item;

import com.google.common.collect.Maps;
import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.item.abstr.Item;

import java.util.Map;

import static com.nobodyhub.payroll.core.exception.PayrollCoreExceptionCode.CONTEXT_NOT_FOUND;

/**
 * @author Ryan
 */
public class ItemContext {
    private final Map<String, Item> context = Maps.newHashMap();

    public void add(Item item) {
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

    /**
     * print a json-like string
     *
     * @return
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{ \"name\": ItemContext(size: " + context.size() + "), context: { ");
        for (Item item : context.values()) {
            sb.append("\t\"" + item.getItemId() + "\" : \"" + item.getValue().toString() + "\"");
        }
        sb.append("}}");
        return sb.toString();
    }
}
