package com.nobodyhub.payroll.core.item;

import com.google.common.collect.Maps;
import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.item.abstr.Item;

import java.util.Map;

import static com.nobodyhub.payroll.core.exception.PayrollCoreExceptionCode.CONTEXT_INCOMPATIBLE;
import static com.nobodyhub.payroll.core.exception.PayrollCoreExceptionCode.CONTEXT_NOT_FOUND;

/**
 * @author Ryan
 */
public class ItemContext {
    private Map<String, Item> context = Maps.newHashMap();
    private ItemFactory factory;

    public void add(Item item) {
        context.put(item.getItemId(), item);
    }

    public <T> void add(String itemId, T value) throws PayrollCoreException {
        Item item = factory.getItem(itemId, value.getClass());
        item.setValue(value);
        context.put(item.getItemId(), item);
    }

    @SuppressWarnings("unchecked")
    public <T> Item<T> get(String itemId, Class<T> clazz) throws PayrollCoreException {
        Item item = context.get(itemId);
        if (item == null) {
            throw new PayrollCoreException(CONTEXT_NOT_FOUND)
                    .addValue("itemId", itemId)
                    .addValue("clazz", clazz);
        }
        if (item.getValueCls() == clazz) {
            return (Item<T>) item;
        }
        throw new PayrollCoreException(CONTEXT_INCOMPATIBLE)
                .addValue("itemId", itemId)
                .addValue("clazz", clazz);
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
